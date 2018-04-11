package com.uet.qpn.uethub.NewsJsoupParser.Parser;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetContentNews extends AsyncTask<String, Void, WebView> {

    private StringBuilder result;

    @Override
    protected WebView doInBackground(String... strings) {
        result = new StringBuilder();
        try {
            Document document = Jsoup.connect(strings[0]).timeout(10000).get();

            Elements element_ = document.select(".title").addClass("adelle");
            result.append(element_.toString());

            Elements element = document.select(".post-content");
            result.append(element.toString());


            //Log.d("xxx", result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(WebView view) {
        super.onPostExecute(view);
    }
}

