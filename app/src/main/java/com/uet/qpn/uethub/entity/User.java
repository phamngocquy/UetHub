package com.uet.qpn.uethub.entity;

import com.uet.qpn.uethub.config.ConfigLoader;
import com.uet.qpn.uethub.config.Configuration;

import java.io.Serializable;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;

    private String msv;

    public User(String msv) {
        this.id = Configuration.USER_KEY;
        this.msv = msv;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }
}
