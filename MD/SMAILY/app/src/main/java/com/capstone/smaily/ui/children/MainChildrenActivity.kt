package com.capstone.smaily.ui.children

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityMainChildrenBinding
import com.capstone.smaily.preferences.ChildrenLoginPref
import com.capstone.smaily.viewmodel.MainViewModel
import kotlin.system.exitProcess

class MainChildrenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainChildrenBinding
    private lateinit var mainChildrenViewModel: MainViewModel

    private var backPress: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainChildrenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.children)

        mainChildrenViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {
            val access = ChildrenLoginPref(this@MainChildrenActivity).getUser().message
            tvTokenChild.text = access
            btnBrowsing.setOnClickListener { startActivity(Intent(this@MainChildrenActivity, WebviewChildrenActivity::class.java)) }
        }

        getSystemApp()
    }

    private fun getSystemApp() {
        val id = ChildrenLoginPref(this).getUser().parentId.toString()
        val accessToken = ChildrenLoginPref(this).getUser().accessToken.toString()

        val listApp = packageManager.getInstalledPackages(0)
        for (i in 0..15){
            var pkg = listApp.get(i)
            Log.d("Istalled app ", pkg.applicationInfo.loadLabel(packageManager).toString())
            val appInstalled = pkg.applicationInfo.loadLabel(packageManager).toString()
            mainChildrenViewModel.setAppChildren(id, appInstalled, false, accessToken)
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