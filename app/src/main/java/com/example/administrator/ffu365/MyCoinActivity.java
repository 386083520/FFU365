package com.example.administrator.ffu365;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.administrator.ffu365.util.view.ViewUtils;
import com.example.administrator.ffu365.util.view.annotation.ViewById;
import com.example.administrator.ffu365.util.view.annotation.event.OnClick;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MyCoinActivity extends AppCompatActivity{
    @ViewById(R.id.coin_number_tv)
    private TextView mCoinNumberTv;
    @ViewById(R.id.web_view)
    private WebView mWebView;

    private WebSettings mWebSettings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coin);
        ViewUtils.inject(this);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setUseWideViewPort(false);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebView.loadUrl("http://app.ffu365.com/pages/about_coin.html");
    }

    @OnClick(R.id.add_coin_bt)
    private void addCoinBtClick(){
        Intent intent = new Intent(this,RechargeCoinActivity.class);
        startActivity(intent);
    }
}
