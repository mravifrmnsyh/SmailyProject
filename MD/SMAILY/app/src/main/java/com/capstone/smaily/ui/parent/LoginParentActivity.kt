package com.capstone.smaily.ui.parent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityLoginParentBinding
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.viewmodel.MainViewModel

class LoginParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginParentBinding
    private lateinit var loginViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loginViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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
            val txtEmail = etEmail.text.toString()
            val txtPassword = etPassword.text.toString()

            if (txtEmail.isEmpty()){
                etEmail.error = resources.getString(R.string.must_be_filled)
                return
            }
            if (txtPassword.isEmpty()){
                etPassword.error = resources.getString(R.string.must_be_filled)
                return
            }
            //code untuk input ke db, set preferences, dan langsung get token
            with(loginViewModel){
                loginParent(txtEmail, txtPassword)
                getParentLogin().observe(this@LoginParentActivity) {
                    val parentLoginPref = ParentLoginPref(this@LoginParentActivity)
                    val id = it.id
                    val name = it.name
                    val email = it.email
                    val accessToken = it.accessToken
                    parentLoginPref.setUser(id, name, email, accessToken)
                }
                isLoading.observe(this@LoginParentActivity) { showLoading(it) }
                message.observe(this@LoginParentActivity) { showToast(it) }
                isIntent.observe(this@LoginParentActivity) {
                    if (it){
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@LoginParentActivity, TokenParentActivity::class.java))
                            finish()
                        }, 2000)
                    }
                }
            }
            //end code
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state : Boolean){
        binding.bars.visibility = if(state) View.VISIBLE else View.GONE
    }

}