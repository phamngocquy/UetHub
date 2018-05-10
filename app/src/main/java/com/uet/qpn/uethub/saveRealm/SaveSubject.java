package com.uet.qpn.uethub.saveRealm;

import android.util.Log;

import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.entity.Subject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SaveSubject {
    public void saveSubject(Subject subject) {
        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            Long count = realm.where(Subject.class).count();
            Log.w("nghia","start save subject " + count);
            subject.setId(String.valueOf(count));
            realm.copyToRealm(subject);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }
    public void getAllNew() {
        Realm realm = Realm.getDefaultInstance();
        try {
            Log.w("nghia","get all of subjects");
            RealmResults<Subject> subjects = realm.where(Subject.class).findAll();
            Log.w("count", String.valueOf(realm.where(Subject.class).count()));
            List<Subject> subjectList = new ArrayList<>();
            Iterator<Subject> iterator = subjects.iterator();
            while (iterator.hasNext()) {
                Subject subject = iterator.next();
                Log.w("nameOfsubject", subject.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }
}
