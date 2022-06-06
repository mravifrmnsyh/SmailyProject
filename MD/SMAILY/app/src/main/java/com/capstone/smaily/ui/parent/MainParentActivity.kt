package com.capstone.smaily.ui.parent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityMainParentBinding
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.ui.main.MainActivity

class MainParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)

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
            R.id.ic_logout -> {
                showToast(resources.getString(R.string.logout))
                val parentLoginPref = ParentLoginPref(this)
                parentLoginPref.delUser()
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                }, 2000)
                finish()

                //code delete preferences

                showToast("logout")
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}