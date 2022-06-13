package com.capstone.smaily.ui.parent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.smaily.R
import com.capstone.smaily.adapter.AppAdapter
import com.capstone.smaily.adapter.UrlAdapter
import com.capstone.smaily.databinding.ActivityBlockAppBinding
import com.capstone.smaily.viewmodel.MainViewModel
import com.capstone.smaily.viewmodel.UrlViewModel
import com.capstone.smaily.viewmodel.ViewModelFactory

class BlockAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlockAppBinding
    private lateinit var blokAppViewModel: MainViewModel

    private val adapterViewModel: UrlViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        blokAppViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.rvApp.layoutManager = LinearLayoutManager(this)

        getData()
        Toast.makeText(this, "This feature is still in development stage", Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getData() {
        val adapter = AppAdapter()
        binding.rvApp.adapter = adapter
        adapterViewModel.getApp()
        adapterViewModel.app.observe(this, {
            adapter.submitList(it)
        })
    }
}