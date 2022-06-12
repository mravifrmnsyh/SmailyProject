package com.capstone.smaily.ui.children

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityWebviewChildrenBinding
import com.capstone.smaily.ml.Model
import com.capstone.smaily.preferences.ChildrenLoginPref
import com.capstone.smaily.viewmodel.MainViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.*
import java.lang.StringBuilder
import java.net.*

class WebviewChildrenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewChildrenBinding
    private var urlChange: String = "https://www.google.com"
    private lateinit var urlViewModel: MainViewModel
    private var message: String = ""

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
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    var state = false
                    if (url != null){
                        urlChange = url
                    }
                    var urlHost = URL(urlChange)
                    var host = urlHost.host
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
                    }
                    if (state){
                        showAlertDialog(R.drawable.ic_baseline_error_outline_24, "Sorry", "Oppsss, this url ${host} is block by your parent")
                    }
                    return super.shouldOverrideUrlLoading(view, url)
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    getHtmlFromWeb(url.toString())
                }
            }
            webView.loadUrl(urlChange)
        }
    }

    private fun getHtmlFromWeb(url : String) {
        Thread(Runnable {
            val fileName = "labels.txt"
            val inputString = application.assets.open(fileName).bufferedReader().use { it.readText() }
            var townList = inputString.split("\n")
            var block = false

            val stringBuilder = StringBuilder()
            try {
                val doc: Document = Jsoup.connect(url).get()
                val title: String = doc.title()
                val links: Elements = doc.select("img[src]")
                stringBuilder.append(title).append("\n")
                for (link in links) {
                    var urlProtocol = URL(link.attr("src"))
                    var prot = urlProtocol.protocol
                    if (!prot.isNullOrEmpty()){
                        try{
                            val urlImage = URL(link.attr("src"))

                            val imageBitmap = BitmapFactory.decodeStream(urlImage.openConnection().getInputStream())
                            var resized = Bitmap.createScaledBitmap(imageBitmap, 150, 150, true)

                            val model = Model.newInstance(this)

                            // Creates inputs for reference.
                            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)

                            var tImage = TensorImage(DataType.FLOAT32)
                            tImage.load(resized)
                            var byteBuffer = tImage.buffer

                            inputFeature0.loadBuffer(byteBuffer)

                            // Runs model inference and gets result.
                            val outputs = model.process(inputFeature0)
                            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                            if (outputFeature0.floatArray[0] > 0.5f){
                                Log.d("Mantap" , townList[0] + link.attr("src"))
                                block = true
                                break

                            } else {
                                Log.d("tidak mantap" , townList[1] + link.attr("src"))
                            }

                            // Releases model resources if no longer used.
                            model.close()

                        } catch (ml: MalformedURLException){
                            ml.printStackTrace()
                        } catch (e: URISyntaxException) {
                            e.printStackTrace()
                        }
                        stringBuilder.append("\n").append("Link :").append(link.attr("src"))
                    }
                }
                if (block){
                    showAlertDialog(R.drawable.ic_baseline_error_outline_24, "Porn Content", "This url detected pornographic!!")
                } else {
                    showToast("Safe")
                }
            } catch (e: IOException) {
                stringBuilder.append("Error : ").append(e.message).append("\n")
            }
            runOnUiThread { message = stringBuilder.toString() }
        }).start()
    }

    private fun showAlertDialog(icon: Int, title: String, message: String) {
        runOnUiThread(Runnable {
            run(){
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder
                    .setIcon(icon)
                    .setCancelable(true)
                    .setTitle(title)
                    .setCancelable(false)
                    .setMessage(message)
                    .setNegativeButton("Ok") { dialog, _ ->
                        dialog.cancel()
                        onBackPressed()
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        })
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()){
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
    private fun showToast(message: String){
        runOnUiThread(Runnable {
            run(){
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        })
    }
}