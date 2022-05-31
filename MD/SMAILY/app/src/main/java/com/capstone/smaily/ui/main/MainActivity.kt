package com.capstone.smaily.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityMainBinding
import com.capstone.smaily.ui.children.TokenChildActivity
import com.capstone.smaily.ui.parent.LoginParentActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnChildren.setOnClickListener { startActivity(Intent(this@MainActivity, TokenChildActivity::class.java)) }
            btnParent.setOnClickListener { startActivity(Intent(this@MainActivity, LoginParentActivity::class.java)) }
        }
    }
}