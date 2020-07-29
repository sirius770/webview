package vip.sirius.web.view

import kotlinx.android.synthetic.main.activity_main.*
import vip.sirius.web.R

class MainActivity : CommActivity() {


    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main;
    }

    override fun initListener() {
        webViewButton.setOnClickListener {
            startActivity(WebViewActivity::class.java)
        }
    }

    override fun initData() {
    }

    override fun initView() {
    }


}