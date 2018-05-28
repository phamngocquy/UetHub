package com.uet.qpn.uethub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.roger.catloadinglibrary.CatLoadingView;
import com.uet.qpn.uethub.entity.NewsEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Locale;

public class OnlinePDFViewerActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pdfviewer);
        setSupportActionBar((Toolbar) findViewById(R.id.viewToolbarPDFOnline));
        init();

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init() {

        // progressBar = findViewById(R.id.prgBar);
        webView = findViewById(R.id.view_pdf_online);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelectorAll('[role=\"toolbar\"]')[0].remove(); })()");

            }
        });


        String gradeUrl = getIntent().getStringExtra("urlGrade");
        //  String gradeUrl = "http://112.137.129.30/viewgrade/public/upload/044300250518Kien%20truc%20huong%20dich%20vu_%20INT3505%201.pdf";
        String title = "Xem điểm thi";

        if (title != null) {
            getSupportActionBar().setTitle(title);
        }

        if (gradeUrl != null) {
            gradeUrl = gradeUrl.replaceAll(" ", "%20");
        }
        webView.loadUrl("https://docs.google.com/viewerng/viewer?url=" + gradeUrl);

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init(String url) {


        // progressBar = findViewById(R.id.prgBar);
        webView = findViewById(R.id.view_pdf_online);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelectorAll('[role=\"toolbar\"]')[0].remove(); })()");

            }
        });


        //  String gradeUrl = "http://112.137.129.30/viewgrade/public/upload/044300250518Kien%20truc%20huong%20dich%20vu_%20INT3505%201.pdf";
        String title = "Xem điểm thi";

        if (title != null) {
            getSupportActionBar().setTitle(title);
        }


        url = url.replaceAll(" ", "%20");
        webView.loadUrl("https://docs.google.com/viewerng/viewer?url=" + url);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("urlGrade")) {
                setContentView(R.layout.activity_online_pdfviewer);
                // extract the extra-data in the Notification
                String myUrl = extras.getString("urlGrade");
                init(myUrl);
            }
        }
    }
}
