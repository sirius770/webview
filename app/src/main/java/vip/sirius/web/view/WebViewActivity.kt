package vip.sirius.web.view

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*
import vip.sirius.web.R

/**
 * Created with IntelliJ IDEA.
 * Author: sirius
 * Date: 2020/7/29
 * Time: 16:55
 */
class WebViewActivity : CommActivity() {

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_web_view
    }

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
        val url = "https://www.baidu.com"

        val webSettings: WebSettings = webView.getSettings()
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webView.setWebChromeClient(webChromeClient)
        webView.setWebViewClient(webViewClient)
        webView.loadUrl(url)
    }


    private val webViewClient = WebViewClient()
    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            setTitle(title)
        }
    }
}