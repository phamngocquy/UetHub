package com.uet.qpn.uethub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.uet.qpn.uethub.NewsJsoupParser.Parser.GetContentNews;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ReadNewsActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private NewsEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        setSupportActionBar((Toolbar) findViewById(R.id.readNewsToolbar));
        init();

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init() {
        progressBar = findViewById(R.id.prgBar);
        webView = findViewById(R.id.readNewsWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);

        Intent intent = getIntent();
        entity = (NewsEntity) intent.getSerializableExtra("news");

        if (entity.getTitle() != null) {
            getSupportActionBar().setTitle(Helper.getNameByIdNews(entity.getNewsName()));
        }


        new GetContentNews().execute(entity.getUrl());

    }

    @SuppressLint("StaticFieldLeak")
    private class GetContentNews extends AsyncTask<String, Void, Void> {

        private String content;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(strings[0]).timeout(10000).get();
                content = Helper.getContentRaw(document, entity.getNewsName());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                String newContent = Helper.getNewsPage(content.toString());
                Log.d("dataHTMK", newContent);
                webView.loadDataWithBaseURL(null, newContent, "text/html", "UTF-8", null);
                progressBar.setVisibility(View.GONE);
            } catch (NullPointerException e) {
                Log.e("Null", "Helper.getNewsPage(content.toString());");
            }

        }
    }
}