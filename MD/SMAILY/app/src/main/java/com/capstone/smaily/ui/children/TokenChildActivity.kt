package com.capstone.smaily.ui.children

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityTokenChildBinding
import com.capstone.smaily.preferences.ChildrenLoginPref
import com.capstone.smaily.ui.parent.TokenParentActivity
import com.capstone.smaily.viewmodel.MainViewModel

class TokenChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTokenChildBinding
    private lateinit var tokenChildViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.children)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tokenChildViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.btnEnter.setOnClickListener { validateToken() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun validateToken() {
        binding.apply {
            val txtToken = etToken.text.toString()
            if (txtToken.isEmpty()){
                etToken.error = resources.getString(R.string.must_be_filled)
                return
            }
            //validasi token yang ada
            with(tokenChildViewModel){
                tokenChildren(txtToken)
                getTokenChildren().observe(this@TokenChildActivity) {
                    val childrenLoginPref = ChildrenLoginPref(this@TokenChildActivity)
                    val message = it.message
                    val accessToken = it.accessToken
                    childrenLoginPref.setUser(message, accessToken)
                }
                isLoading.observe(this@TokenChildActivity){ showLoading(it) }
                message.observe(this@TokenChildActivity) { showToast(it) }
                isIntent.observe(this@TokenChildActivity) {
                    if (it) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@TokenChildActivity, MainChildrenActivity::class.java))
                            finish()
                        }, 2000)
                    }
                }
            }
            //endvalidate
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state : Boolean){
        binding.bars.visibility = if(state) View.VISIBLE else View.GONE
    }
}