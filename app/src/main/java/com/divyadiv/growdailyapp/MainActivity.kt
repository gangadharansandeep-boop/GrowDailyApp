package com.divyadiv.growdailyapp

import android.content.Context
import android.graphics.Color // Added import
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import java.util.UUID

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "GrowDailyAppPrefs"
        private const val USER_ID_KEY = "UserId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        // Make WebView background transparent until web page loads
        webView.setBackgroundColor(Color.TRANSPARENT)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString(USER_ID_KEY, null)

        if (userId == null) {
            userId = UUID.randomUUID().toString()
            sharedPreferences.edit().putString(USER_ID_KEY, userId).apply()
        }

        val baseUrl = "https://zhb5y54rdbakfbjgvgrrj6-growdaily.streamlit.app"
        val urlWithUserId = "$baseUrl?id=$userId"

        webView.loadUrl(urlWithUserId)
    }
}