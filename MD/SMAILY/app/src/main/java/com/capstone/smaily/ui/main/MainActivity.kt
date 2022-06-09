package com.capstone.smaily.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityMainBinding
import com.capstone.smaily.ui.children.TokenChildActivity
import com.capstone.smaily.ui.parent.LoginParentActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var backPress: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            btnChildren.setOnClickListener { startActivity(Intent(this@MainActivity, TokenChildActivity::class.java)) }
            btnParent.setOnClickListener { startActivity(Intent(this@MainActivity, LoginParentActivity::class.java)) }
        }
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}