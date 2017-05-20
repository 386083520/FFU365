package com.example.administrator.ffu365;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Administrator on 2017/5/20.
 */

public class DetailLinkActivity extends AppCompatActivity{
    private WebView webView;
    private WebSettings mWebSettings;
    private String mUrl;
    public final static String URL_KEY = "URL_KEY";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_link);
        webView= (WebView) findViewById(R.id.web_view);
        mUrl = getIntent().getStringExtra(URL_KEY);
        mWebSettings = webView.getSettings();
        mWebSettings.setUseWideViewPort(false);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        webView.loadUrl(mUrl);
    }
}
