package com.capstone.smaily.ui.parent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityRegisterParentBinding

class RegisterParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            btnRegister.setOnClickListener { register() }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun register() {
        binding.apply {
            val txtUsername = etUsername.text
            val txtEmail = etEmail.text.toString()
            val txtPassword = etPassword.text.toString()

            if (txtUsername.isEmpty()){
                etUsername.error = resources.getString(R.string.must_be_filled)
                return
            }
            if (txtEmail.isEmpty()){
                etEmail.error = resources.getString(R.string.must_be_filled)
                return
            }
            if (txtPassword.isEmpty()){
                etPassword.error = resources.getString(R.string.must_be_filled)
                return
            }
            //code untuk input ke db

            //end code
            startActivity(Intent(this@RegisterParentActivity, LoginParentActivity::class.java))
        }
    }
}
