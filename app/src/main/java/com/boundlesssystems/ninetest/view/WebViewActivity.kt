package com.boundlesssystems.ninetest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.boundlesssystems.ninetest.R
import kotlinx.android.synthetic.main.activity_webview.*
import android.webkit.WebViewClient



/** This activity will show the actual news story when an item is clicked on the recyclerview in the main screen */
/** State of the webview is preserved when the device is rotated */
class WebViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webview.webViewClient = WebViewClient()
        val url : String? = intent.getStringExtra("url")

        if (savedInstanceState == null) {
            url?.let { webview.loadUrl(url) }
        }

    }

    //Save the state of the webview
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
    }

    //Restore the state of the webview
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webview.restoreState(savedInstanceState)
    }

}
