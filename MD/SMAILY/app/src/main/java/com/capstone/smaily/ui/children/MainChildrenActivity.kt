package com.capstone.smaily.ui.children

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityMainChildrenBinding
import com.capstone.smaily.preferences.ChildrenLoginPref
import kotlin.system.exitProcess

class MainChildrenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainChildrenBinding
    private var backPress: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainChildrenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.children)

        binding.apply {
            val access = ChildrenLoginPref(this@MainChildrenActivity).getUser().message
            tvTokenChild.text = access
            btnBrowsing.setOnClickListener { startActivity(Intent(this@MainChildrenActivity, WebviewChildrenActivity::class.java)) }
        }
    }

    override fun onBackPressed() {
        if (backPress + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finishAffinity()
            exitProcess(0)
        } else {
            showToast(resources.getString(R.string.double_press_to_exit))
        }

        backPress = System.currentTimeMillis()
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}