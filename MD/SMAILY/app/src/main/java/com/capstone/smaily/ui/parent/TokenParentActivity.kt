package com.capstone.smaily.ui.parent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityTokenParentBinding

class TokenParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTokenParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)

        binding.apply {
            btnEnter.setOnClickListener {
                //code untuk verif token

                //end code
                startActivity(Intent(this@TokenParentActivity, MainParentActivity::class.java)) }
        }
    }
}