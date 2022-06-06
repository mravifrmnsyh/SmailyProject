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
import com.capstone.smaily.databinding.ActivityTokenParentBinding
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.viewmodel.MainViewModel

class TokenParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTokenParentBinding
    private lateinit var tokenViewModel: MainViewModel
    private lateinit var loginPref: ParentLoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)

        tokenViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        loginPref = ParentLoginPref(this)

        setButton(true)

        binding.apply {
            //code untuk get token
            val id = loginPref.getUser().id.toString()
            val token = loginPref.getUser().accesstoken.toString()
//            tokenViewModel.getTokenParent(id, token)
//            tokenViewModel.isLoading.observe(this@TokenParentActivity) {
//                showLoading(it)
//            }
            //code untuk verif token
//            tokenViewModel.getTokenParent().observe(this@TokenParentActivity) {
//                tvToken.text = it.connectToken
//            }
            btnToken.setOnClickListener {
                with(tokenViewModel){
                    tokenParent(id, token)
                    isLoading.observe(this@TokenParentActivity) { showLoading(it) }
                    getTokenParent().observe(this@TokenParentActivity) { tvToken.text = it.connectToken }
                    message.observe(this@TokenParentActivity) { showToast(it) }
                    isIntent.observe(this@TokenParentActivity) {
                        if (it) {
                            setButton(false)
                        }
                    }
                }
            }
            btnEnter.setOnClickListener {
                tokenViewModel.isIntent.observe(this@TokenParentActivity) {
                    if (it) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@TokenParentActivity, MainParentActivity::class.java))
                            finish()
                        }, 2000)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setButton(state: Boolean) {
        if (state){
            binding.btnToken.visibility = View.VISIBLE
            binding.btnEnter.visibility = View.GONE
        } else {
            binding.btnEnter.visibility = View.VISIBLE
            binding.btnToken.visibility = View.GONE
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state : Boolean){
        binding.bars.visibility = if(state) View.VISIBLE else View.GONE
    }
}