package com.supporter.marcus.classsupport.ui.detail

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.supporter.marcus.classsupport.R
import kotlinx.android.synthetic.main.fragment_web_view.*


class WebViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 -> {
                (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_action_close)
                (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
            }
        }
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = WebViewFragmentArgs.fromBundle(arguments).weburl
        detial_webview.loadUrl(url)
        // Enable Javascript
        val webSettings = detial_webview.settings
        webSettings.javaScriptEnabled = true

        // Force links and redirects to open in the WebView instead of in a browser
        detial_webview.webViewClient = WebViewClient()

    }


}
