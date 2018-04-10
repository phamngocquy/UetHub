package com.uet.qpn.uethub.entity;

import java.io.Serializable;

public class NewsEntity implements Serializable {

    private String title;
    private String description;
    private String categories;
    private String publictime;
    private String author;
    private String url;
    private String newsName;

    public NewsEntity() {
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
}
