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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)

        tokenViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setButton(true)

        val id = ParentLoginPref(this@TokenParentActivity).getUser().id.toString()
        val token = ParentLoginPref(this@TokenParentActivity).getUser().accesstoken.toString()
        cekChildren(id, token)

        binding.apply {
            btnToken.setOnClickListener { getToken(id, token)}
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

    private fun cekChildren(id: String, accessToken: String){
        //cek jika idChildren tidak kosong, langsung ke main parent
        with(tokenViewModel){
            profilParent(id, accessToken)
            getProfilParent().observe(this@TokenParentActivity) {
                if (it.children != null){
                    startActivity(Intent(this@TokenParentActivity, MainParentActivity::class.java))
                }
            }
        }
    }

    private fun getToken(id: String, accessToken: String) {
        with(tokenViewModel){
            tokenParent(id, accessToken)
            isLoading.observe(this@TokenParentActivity) { showLoading(it) }
            getTokenParent().observe(this@TokenParentActivity) { binding.tvToken.text = it.connectToken }
            isIntent.observe(this@TokenParentActivity) { if (it) setButton(false) }
            message.observe(this@TokenParentActivity) { showToast(it) }
        }
    }
}