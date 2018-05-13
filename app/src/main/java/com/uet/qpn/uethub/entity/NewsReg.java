package com.uet.qpn.uethub.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NewsReg extends RealmObject {

    @PrimaryKey
    private String newsName;

    private Boolean checked;

    public NewsReg() {
    }

    public NewsReg(String newsName, Boolean checked) {
        this.newsName = newsName;
        this.checked = checked;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
