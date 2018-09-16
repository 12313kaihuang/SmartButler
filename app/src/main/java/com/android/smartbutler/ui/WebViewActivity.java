package com.android.smartbutler.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.smartbutler.R;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：WebViewActivity
 * 创建者：HY
 * 创建时间：2018/9/16 15:02
 * 描述：  新闻详情
 */

public class WebViewActivity extends BaseActivity {

    //进度
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initView();
    }

    //初始化VIew
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        progressBar = findViewById(R.id.mProgressBar);
        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");


        //设置标题
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(title);

        //进行加载网页的逻辑
        //支持js
        webView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        webView.setWebChromeClient(new WebViewClient());
        //加载网页
        webView.loadUrl(url);

        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                //接受这个事件
                return true;
            }
        });
    }

    public class WebViewClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
