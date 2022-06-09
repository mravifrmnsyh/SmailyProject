package com.capstone.smaily.ui.parent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.smaily.R
import com.capstone.smaily.adapter.UrlAdapter
import com.capstone.smaily.databinding.ActivityBlokLinkParentBinding
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.viewmodel.MainViewModel
import com.capstone.smaily.viewmodel.UrlViewModel
import com.capstone.smaily.viewmodel.ViewModelFactory

class BlokLinkParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlokLinkParentBinding
    private lateinit var blockUrlViewModel: MainViewModel

    private val adapterViewModel: UrlViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlokLinkParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        blockUrlViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.rvLink.layoutManager = LinearLayoutManager(this)

        getData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_parent_add_link, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_announcement -> {
                val alertDialogBuilder = AlertDialog.Builder(this)

                alertDialogBuilder
                    .setIcon(R.drawable.ic_baseline_announcement_24)
                    .setCancelable(true)
                    .setTitle("Announcement")
                    .setMessage("To add the url link, please just use format hostname, example : m.facebook.com")
                    .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss()}
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                return super.onOptionsItemSelected(item)
            }
            R.id.ic_add_link -> {
                showAlertDialog("Block link")
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getData() {
        val adapter = UrlAdapter()
        binding.rvLink.adapter = adapter
        adapterViewModel.getUrl()
        adapterViewModel.url.observe(this, {
            adapter.submitList(it)
        })
    }

    private fun showAlertDialog(title: String){
        val alertDialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.form_link, null)

        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save") { dialog, _ ->
                //code add to db
                val link = dialogView.findViewById<EditText>(R.id.et_link).text.toString()
                val id = ParentLoginPref(this).getUser().id.toString()
                val accessToken = ParentLoginPref(this).getUser().accesstoken.toString()
                blockUrlViewModel.apply {
                    setUrlParent(id, link, true, accessToken)
                    message.observe(this@BlokLinkParentActivity) { showToast(it) }
                    getData()
                }
                //end code
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                showToast("No save")
                dialog.dismiss()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}