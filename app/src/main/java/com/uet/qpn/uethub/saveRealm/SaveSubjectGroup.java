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
                Log.w("SG", "Start save SG" );
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
    public List<SubjectGroup> getAllSubjectGroup() {
        Realm realm = Realm.getDefaultInstance();
        List<SubjectGroup> subjectGroupList = new ArrayList<>();
        try {
            RealmResults<SubjectGroup> subjectGroups = realm.where(SubjectGroup.class).findAll();
            Iterator<SubjectGroup> iterator = subjectGroups.iterator();
            Log.w("SG", "Get all Subject Group" + realm.where(SubjectGroup.class).count());
            while (iterator.hasNext()) {
                SubjectGroup subjectGroup = iterator.next();
                SubjectGroup subjectGroup_tmp = new SubjectGroup();
                subjectGroup_tmp.setMsv(subjectGroup.getMsv());
                subjectGroup_tmp.setSbdUser(subjectGroup.getSbdUser());
                subjectGroup_tmp.setExamDay(subjectGroup.getExamDay());
                subjectGroup_tmp.setExamRoom(subjectGroup.getExamRoom());
                subjectGroup_tmp.setTypeExam(subjectGroup.getTypeExam());
                subjectGroup_tmp.setSubjectName(subjectGroup.getSubjectName());
                subjectGroup_tmp.setSubjectCode(subjectGroup.getSubjectCode());
                subjectGroup_tmp.setTerm(subjectGroup.getTerm());
                subjectGroup_tmp.setUpdate_on(subjectGroup.getUpdate_on());
                subjectGroupList.add(subjectGroup_tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
        for (int i = 0; i < subjectGroupList.size(); i++){
            Log.w("en", subjectGroupList.get(i).getSubjectName());
            Log.w("msv", subjectGroupList.get(i).getMsv());
        }
        return subjectGroupList;
    }

    public void deleteRealm(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SubjectGroup> rows = realm.where(SubjectGroup.class).findAll();
                rows.deleteAllFromRealm();
            }
        });
        realm.close();
    }
}
