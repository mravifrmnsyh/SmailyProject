package com.capstone.smaily.ui.parent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityMainParentBinding
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.ui.main.MainActivity
import com.capstone.smaily.viewmodel.MainViewModel
import java.lang.StringBuilder
import kotlin.system.exitProcess

class MainParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainParentBinding
    private lateinit var mainViewModel: MainViewModel
    private var backPress: Long = 0

    private lateinit var token: String
    private lateinit var idParent: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var idChildren: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainParentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        idParent = ParentLoginPref(this).getUser().id.toString()
        token = ParentLoginPref(this).getUser().accesstoken.toString()
        with(mainViewModel) {
            profilParent(idParent, token)
            getProfilParent().observe(this@MainParentActivity) {
                val parenLoginPref = ParentLoginPref(this@MainParentActivity)
                name = it.name
                email = it.email
                idChildren = it.children
                parenLoginPref.setIdChild(it.children)
            }
        }
        binding.apply {
            blockLink.setOnClickListener{ startActivity(Intent(this@MainParentActivity, BlokLinkParentActivity::class.java)) }
            blockApp.setOnClickListener { showToast("block app") }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_parent, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_profil -> {
                showToast(resources.getString(R.string.profil))
                val parentProfil = StringBuilder().append("Id : " + idParent,"\nName : "+ name, "\nEmail : "+ email,"\nChildren : "+ idChildren)
                showAlertDialog("Profil", parentProfil.toString())
            }
            R.id.ic_logout -> {
                showToast(resources.getString(R.string.logout))
                val parentLoginPref = ParentLoginPref(this)
                parentLoginPref.delUser()
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 2000)
                showToast("logout")
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (backPress + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finishAffinity()
            exitProcess(0)
        } else {
            showToast(resources.getString(R.string.double_press_to_exit))
        }

        backPress = System.currentTimeMillis()
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showAlertDialog(title: String, message: String){
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder
            .setIcon(R.mipmap.ic_smaily)
            .setCancelable(true)
            .setTitle(title)
            .setCancelable(false)
            .setMessage(message)
            .setNegativeButton("Ok") { dialog, _ ->
                dialog.cancel()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}