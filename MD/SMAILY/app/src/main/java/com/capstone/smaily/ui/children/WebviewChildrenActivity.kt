package com.capstone.smaily.ui.children

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityWebviewChildrenBinding
import com.capstone.smaily.preferences.ChildrenLoginPref
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.response.UrlResponse
import com.capstone.smaily.viewmodel.MainViewModel
import com.capstone.smaily.viewmodel.UrlViewModel
import com.capstone.smaily.viewmodel.ViewModelFactory
import java.net.URL
import java.util.ArrayList

class WebviewChildrenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewChildrenBinding
    private var urlChange: String = "https://www.google.com"
    private lateinit var urlViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewChildrenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.children_brows)

        urlViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {
            with(webView.settings){
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
            }

            webView.webViewClient = object:WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    var state = false
                    if (url != null){
                        urlChange = url
                        Log.d("LisUrl", url.toString())
                    }

                    var urlHost = URL(urlChange)
                    var host = urlHost.host
                    Log.d("host", host.toString())
                    //block url
                    val idChild = ChildrenLoginPref(this@WebviewChildrenActivity).getUser().childrenId.toString()
                    val accessToken = ChildrenLoginPref(this@WebviewChildrenActivity).getUser().accessToken.toString()
                    urlViewModel.dataUrlChildren(idChild, accessToken)
                    urlViewModel.getDataUrl().observe(this@WebviewChildrenActivity) {
                        for (list in it){
                            if (host.equals(list.url)){
                                state = true
                            }
                        }
                        Log.d("state", state.toString())
                    }
                    if (state){
                        val alertDialogBuilder = AlertDialog.Builder(this@WebviewChildrenActivity)

                        alertDialogBuilder
                            .setTitle("Sorry")
                            .setMessage("Opss, this url ${host} is block with your parent!!")
                            .setCancelable(false)
                            .setPositiveButton("Ok") { dialog, _ ->
                                onBackPressed()
                                dialog.dismiss()
                            }
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.show()
                    }
                    Log.d("state2", state.toString())
                }
            }
            webView.loadUrl(urlChange)
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()){
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}