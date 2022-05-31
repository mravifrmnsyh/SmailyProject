package com.capstone.smaily.ui.parent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityLoginParentBinding

class LoginParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            btnLogin.setOnClickListener { login() }
            tvClickRegist.setOnClickListener { startActivity(Intent(this@LoginParentActivity, RegisterParentActivity::class.java)) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun login(){
        binding.apply {
            val txtUsername = etUsername.text
            val txtPassword = etPassword.text.toString()

            if (txtUsername.isEmpty()){
                etUsername.error = resources.getString(R.string.must_be_filled)
                return
            }
            if (txtPassword.isEmpty()){
                etPassword.error = resources.getString(R.string.must_be_filled)
                return
            }
            //code untuk input ke db, set preferences, dan langsung get token

            //end code
            startActivity(Intent(this@LoginParentActivity, TokenParentActivity::class.java))
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state : Boolean){
        binding.bars.visibility = if(state) View.VISIBLE else View.GONE
    }

}