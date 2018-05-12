package com.uet.qpn.uethub.config;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.saveRealm.SaveNew;
import com.uet.qpn.uethub.saveRealm.SaveSubject;
import com.uet.qpn.uethub.saveRealm.SaveSubjectGroup;
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
        /*RealmDemo realmDemo = new RealmDemo();
        realmDemo.saveSubjects(new Subject(UUID.randomUUID().toString(),"", "", "", "phuong", "", "", ""));
        realmDemo.getAllSubjects();
        SaveNew saveNew = new SaveNew();
        saveNew.getAllNew();
        SaveSubject saveSubject = new SaveSubject();
        saveSubject.getAllNew();
        SaveSubjectGroup saveSubjectGroup = new SaveSubjectGroup();
        saveSubjectGroup.getAllSubjectGroup();*/
//        SaveNew saveNew = new SaveNew();
//        Log.w("why", "where");
//        saveNew.getAllNews();
    }
}
