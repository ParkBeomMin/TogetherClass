package com.example.park.togetherclass;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
