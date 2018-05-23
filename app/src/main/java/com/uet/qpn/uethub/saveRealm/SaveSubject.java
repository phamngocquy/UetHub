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
        Subject subject_tmp = realm.where(Subject.class).equalTo("code", subject.getCode()).findFirst();
        Log.w("why", "w");
        if(subject_tmp == null) {
            try {
                realm.beginTransaction();
                Long count = realm.where(Subject.class).count();
                Log.w("nghia", "start save subject " + count);
                subject.setId(String.valueOf(count));
                realm.copyToRealm(subject);
                realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                realm.close();
            }
        }
    }
    public List<Subject> getAllSubjects() {
        Realm realm = Realm.getDefaultInstance();
        List<Subject> subjectList = new ArrayList<>();
        try {
            Log.w("nghia","get all of subjects");
            RealmResults<Subject> subjects = realm.where(Subject.class).findAll();
            Log.w("count", String.valueOf(realm.where(Subject.class).count()));
            Iterator<Subject> iterator = subjects.iterator();
            while (iterator.hasNext()) {
                Subject subject = iterator.next();
                Subject subject_tmp = new Subject();
                subject_tmp.setCode(subject.getCode());
                subject_tmp.setPublic_time(subject.getPublic_time());
                subject_tmp.setLocal_url(subject.getLocal_url());
                subject_tmp.setName(subject.getName());
                subject_tmp.setTerm(subject.getTerm());
                subject_tmp.setUrl(subject.getUrl());
                subject_tmp.setUpdate_on(subject.getUpdate_on());
                subjectList.add(subject_tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
        for (int i = 0; i < subjectList.size(); i++){
            Log.w("en", subjectList.get(i).getName());
        }
        return subjectList;
    }

    public void deleteRealm(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Subject> rows = realm.where(Subject.class).findAll();
                rows.deleteAllFromRealm();
            }
        });
        realm.close();
    }
}
