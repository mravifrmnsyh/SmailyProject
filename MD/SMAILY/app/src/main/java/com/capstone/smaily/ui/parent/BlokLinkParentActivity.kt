package com.capstone.smaily.ui.parent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityBlokLinkParentBinding

class BlokLinkParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlokLinkParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlokLinkParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            R.id.ic_add_link -> {
//                showToast(resources.getString(R.string.setting))
//                Handler(Looper.getMainLooper()).postDelayed({
//                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
//                }, 2000)

                showAlertDialog("Block link")
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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
                //end code
                showToast(link)
                dialog.cancel()
            }
            .setNegativeButton("No") { dialog, _ ->
                showToast("No save")
                dialog.cancel()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}