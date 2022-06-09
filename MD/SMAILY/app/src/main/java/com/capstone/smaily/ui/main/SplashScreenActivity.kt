package com.capstone.smaily.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.capstone.smaily.databinding.ActivitySplashScreenBinding
import com.capstone.smaily.preferences.ChildrenLoginPref
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.ui.children.MainChildrenActivity
import com.capstone.smaily.ui.parent.MainParentActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val childLoginPref = ChildrenLoginPref(this).getUser().accessToken
        val parentLoginPref = ParentLoginPref(this).getUser().accesstoken

        if (!childLoginPref.isNullOrEmpty() && parentLoginPref.isNullOrEmpty()){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainChildrenActivity::class.java))
                finish()
            }, 2000)
        } else if (!parentLoginPref.isNullOrEmpty() && childLoginPref.isNullOrEmpty()){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainParentActivity::class.java))
                finish()
            }, 2000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }
    }
}