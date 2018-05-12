package com.uet.qpn.uethub.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NewsEntity extends RealmObject implements Serializable,Comparable<NewsEntity>{

    @PrimaryKey
    private String id;
    private String title;
    private String description;
    private String categories;
    private String publictime;
    private String author;
    private String url;
    private String newsName;

    public NewsEntity() {
    }

    public NewsEntity(String id, String title, String description, String categories, String publictime, String author, String url, String newsName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.publictime = publictime;
        this.author = author;
        this.url = url;
        this.newsName = newsName;
    }

    public NewsEntity(String url, String newsName) {
        this.url = url;
        this.newsName = newsName;
    }

    public NewsEntity(String title, String description, String categories, String publictime, String author) {
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.publictime = publictime;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getPublictime() {
        return publictime;
    }

    public void setPublictime(String publictime) {
        this.publictime = publictime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    @Override
    public int compareTo(@NonNull NewsEntity o) {
        SimpleDateFormat format = new SimpleDateFormat("hh'h'mm dd-MM-yyyy");
        Date date1 = null;
        Date date2 = null;
        try {
           date1 = format.parse(getPublictime());
           date2 = format.parse(o.getPublictime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2.compareTo(date1);
    }
}
