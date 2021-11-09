package com.usth.wordpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.usth.wordpress.R;
import com.usth.wordpress.ui.reader.FollowingFragment;

public class WebviewPostContent extends AppCompatActivity {

    private WebView mWebview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String content_html = intent.getStringExtra("content_html");
        String title = intent.getStringExtra("title");
        setTitle(title);

        setContentView(R.layout.activity_webview);


        mWebview = (WebView) findViewById(R.id.postContent);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadData(content_html,"text/html","UTF-8");
    }
}