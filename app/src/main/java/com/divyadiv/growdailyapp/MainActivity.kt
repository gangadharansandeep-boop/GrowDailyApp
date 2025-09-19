package com.divyadiv.growdailyapp

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

/**
 * Main activity for the GrowDailyApp.
 * This activity hosts a WebView to display the GrowDaily streamlit app,
 * persisting a unique user ID across sessions.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "GrowDailyAppPrefs"
        private const val USER_ID_KEY = "UserId"
    }

    /**
     * Called when the activity is first created.
     *
     * This method initializes the activity, sets the content view, configures the WebView,
     * and ensures a persistent unique ID is used for the web session.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in [onSaveInstanceState].  <b><i>Note: Otherwise it is null.</i></b>
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

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