package com.uet.qpn.uethub;

import android.util.Log;

import com.uet.qpn.uethub.entity.NewsEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;

public class Helper {

    public static ArrayList<NewsEntity> getNewsEntity(String response) {
        ArrayList<NewsEntity> entities = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                NewsEntity entity = new NewsEntity();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                entity.setAuthor(jsonObject.getString("author"));
                entity.setCategories(jsonObject.getString("categories"));
                entity.setDescription(jsonObject.getString("description"));
                entity.setTitle(jsonObject.getString("title"));
                entity.setUrl(jsonObject.getString("url"));
                entity.setNewsName(jsonObject.getString("newsName"));

                Long dataNum = Long.valueOf(jsonObject.getString("date"));
                Date date = new Date(dataNum);
                entity.setPublictime(date.toString());


                Log.d("xxx", entity.toString());
                entities.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return entities;
    }

    public static String getNewsPage(String content) {
        return "<!DOCTYPE html><html><head></head><body>" + content + "</body></html>";

    }

    public static String getContentRaw(Document document, String newsName) {
        StringBuilder result = new StringBuilder();
        Elements elements = new Elements();
        Elements elements_ = new Elements();
        switch (newsName) {
            case "UET":
                elements = document.select(".single-content-title");
                elements_ = document.select(".single-post-content-text").addClass("content-pad");
                break;
            case "FIT":
                elements = document.select(".entry-header");
                elements_ = document.select(".entry-content").addClass("clearfix");
                break;
            case "FET":
                elements = document.select(".post-content").addClass("clearfix").select("h2");
                elements_ = document.select(".entry").addClass("clearfix");

                break;
            case "FEPN":
                elements = document.select(".title").addClass("adelle");
                elements_ = document.select(".post-content");
                break;
        }

        result.append(elements.toString());
        result.append(elements_.toString());

        return result.toString();
    }

    public static String getNameByIdNews(String newsName) {
        switch (newsName) {
            case "UET":
                return "Trường đại học Công Nghệ";
            case "FIT":
                return "Khoa Công Nghệ Thông Tin";
            case "FET":
                return "Khoa Điện tử - Viễn Thông";
            case "FEPN":
                return "Khoa Vật Lỹ Kỹ Thuật và Công Nghệ NANO";
        }
        return "Unknown";
    }
}
