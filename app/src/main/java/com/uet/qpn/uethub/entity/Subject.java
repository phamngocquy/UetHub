package com.uet.qpn.uethub.entity;

public class Subject {

    private String code;
    private String public_time;
    private String local_url;
    private String name;
    private String term;
    private String url;
    private String update_on;

    public Subject() {
    }

    public Subject(String code, String public_time, String local_url, String name, String term, String url, String update_on) {
        this.code = code;
        this.public_time = public_time;
        this.local_url = local_url;
        this.name = name;
        this.term = term;
        this.url = url;
        this.update_on = update_on;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPublic_time() {
        return public_time;
    }

    public void setPublic_time(String public_time) {
        this.public_time = public_time;
    }

    public String getLocal_url() {
        return local_url;
    }

    public void setLocal_url(String local_url) {
        this.local_url = local_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdate_on() {
        return update_on;
    }

    public void setUpdate_on(String update_on) {
        this.update_on = update_on;
    }
}
