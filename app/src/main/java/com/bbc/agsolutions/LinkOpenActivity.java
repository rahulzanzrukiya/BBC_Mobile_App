package com.bbc.agsolutions;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class LinkOpenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_open);

        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        WebView webView = (WebView) findViewById(R.id.webView);

        String link = getIntent().getStringExtra("link");

        Log.e("link", "link: "+link);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(link);

    }
}