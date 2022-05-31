package com.capstone.smaily.ui.children

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityTokenChildBinding

class TokenChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTokenChildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.children)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnEnter.setOnClickListener { validateToken() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun validateToken() {
        binding.apply {
            val txtToken = etToken.text
            if (txtToken.isEmpty()){
                etToken.error = resources.getString(R.string.must_be_filled)
                return
            }
            //validasi token yang ada

            //endvalidate
            startActivity(Intent(this@TokenChildActivity, MainChildrenActivity::class.java))
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state : Boolean){
        binding.bars.visibility = if(state) View.VISIBLE else View.GONE
    }
}