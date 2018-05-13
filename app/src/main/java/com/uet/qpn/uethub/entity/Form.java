package com.uet.qpn.uethub.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Form extends RealmObject {
    @PrimaryKey
    private String id;


    private String name;
    private String url;
    private String local_url;
    private String createTime;
    private String updatedTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Form() {
    }

    public Form(String id, String name, String url, String local_url, String createTime, String updatedTime) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.local_url = local_url;
        this.createTime = createTime;
        this.updatedTime = updatedTime;
    }

    public Form(String name, String url, String local_url, String createTime, String updatedTime) {
        this.name = name;
        this.url = url;
        this.local_url = local_url;
        this.createTime = createTime;
        this.updatedTime = updatedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocal_url() {
        return local_url;
    }

    public void setLocal_url(String local_url) {
        this.local_url = local_url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
