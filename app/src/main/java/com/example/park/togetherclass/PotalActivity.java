package com.example.park.togetherclass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class PotalActivity extends AppCompatActivity {
    WebView webView;
    final String MAIN_URL = "https://portal.hanyang.ac.kr/sso/lgin.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potal);
        init();
    }

    void init() {
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(MAIN_URL);
    }
}
