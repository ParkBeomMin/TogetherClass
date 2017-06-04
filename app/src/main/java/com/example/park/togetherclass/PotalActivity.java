package com.example.park.togetherclass;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PotalActivity extends AppCompatActivity {
    WebView webView;
    ProgressDialog dialog;
    final String MAIN_URL = "https://portal.hanyang.ac.kr/sso/lgin.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potal);
        setActionBar();
        init();
    }

    void init() {
        dialog = new ProgressDialog(this);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(MAIN_URL);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.setMessage("Loading...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) dialog.dismiss();
            }
        });
    }
    void setActionBar() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.MyBlue)));

        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        actionBar.setCustomView(view);
    }
}
