package com.capstone.smaily.ui.children

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityWebviewChildrenBinding

class WebviewChildrenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewChildrenBinding
    private var urlChange: String = "https://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewChildrenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.children_brows)

        binding.apply {
            with(webView.settings){
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
            }

            webView.webViewClient = object:WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
//                    super.onPageFinished(view, url)
                    if (url != null){
                        urlChange = url
                    }
                    //block url
                    if (urlChange.equals("https://m.facebook.com/")){
                        showToast("this url is block")
                        webView.loadUrl("https://twitter.com/")
                    }
//                    showToast(urlChange)
                    Log.d("Url", urlChange)
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