package com.uet.qpn.uethub.entity;

public class NewsEntity {

    private String title;
    private String description;
    private String categories;
    private String publictime;
    private String author;

    public NewsEntity() {
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
}
