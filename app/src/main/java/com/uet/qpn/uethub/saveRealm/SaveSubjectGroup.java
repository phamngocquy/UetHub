package com.uet.qpn.uethub.saveRealm;

import android.util.Log;

import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.entity.SubjectGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SaveSubjectGroup {
    public void saveSubjectGroup(SubjectGroup subjectGroup) {
        Realm realm = Realm.getDefaultInstance();
        SubjectGroup subjectGroup_tmp = realm.where(SubjectGroup.class).equalTo("msv", subjectGroup.getMsv()).equalTo("subjectCode", subjectGroup.getSubjectCode()).findFirst();
        if(subjectGroup_tmp == null) {
            try {
                realm.beginTransaction();
                Long count = realm.where(SubjectGroup.class).count();
                subjectGroup.setId(String.valueOf(count));
                realm.copyToRealm(subjectGroup);
                realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                realm.close();
            }
        }
    }
    public void getAllSubjectGroup() {
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<SubjectGroup> subjectGroups = realm.where(SubjectGroup.class).findAll();
            List<SubjectGroup> subjectGroupList = new ArrayList<>();
            Iterator<SubjectGroup> iterator = subjectGroupList.iterator();
            while (iterator.hasNext()) {
                SubjectGroup subjectGroup = iterator.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }
}