package com.uet.qpn.uethub.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ConfigGroup extends RealmObject{
    @PrimaryKey
    private String type;

    private String value;

    public ConfigGroup() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ConfigGroup(String type, String value) {

        this.type = type;
        this.value = value;
    }
}
