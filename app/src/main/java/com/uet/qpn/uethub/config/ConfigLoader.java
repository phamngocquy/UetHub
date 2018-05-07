package com.uet.qpn.uethub.config;

import android.app.Application;

import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.test.RealmDemo;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ConfigLoader extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);
        RealmDemo realmDemo = new RealmDemo();
        realmDemo.saveSubjects(new Subject(UUID.randomUUID().toString(),"", "", "", "phuong", "", "", ""));
        realmDemo.getAllSubjects();
    }
}
