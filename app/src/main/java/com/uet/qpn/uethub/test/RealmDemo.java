package com.uet.qpn.uethub.test;

import android.util.Log;

import com.uet.qpn.uethub.entity.Subject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDemo {
    public void saveSubjects(Subject subject) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            Log.w("phuongpv","bat dau transaction");
            realm.copyToRealm(subject);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }

    }

    public void getAllSubjects() {
        Realm realm = Realm.getDefaultInstance();
        try {
            Log.w("phuongpv","lay du lieu");
            RealmResults<Subject> abc = realm.where(Subject.class).findAll();
            List<Subject> subjects = new ArrayList<>();
            Iterator<Subject> def = abc.iterator();
            Log.w("a","vck");
            while (def.hasNext()) {
                Subject subject = def.next();
                Log.w("name", subject.getName());
            }
            Log.w("name","vck");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }
}
