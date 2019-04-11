package com.bernovia.mylestone.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)


        binding.bannerWebView.loadUrl("https://www.websitepolicies.com/policies/view/FkQGsPIu")


        // Enable Javascript
        val webSettings = binding.bannerWebView.settings
        webSettings.javaScriptEnabled = true

        // Force links and redirects to open in the WebView instead of in a browser
        binding.bannerWebView.webViewClient = WebViewClient()


        binding.backButton.setOnClickListener { view -> finish() }


    }
}
