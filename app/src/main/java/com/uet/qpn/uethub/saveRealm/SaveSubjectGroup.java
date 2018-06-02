package com.uet.qpn.uethub.saveRealm;

import android.content.Context;
import android.util.Log;

import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.entity.SubjectGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SaveSubjectGroup {
    private Context context;

    public SaveSubjectGroup() {

    }

    public SaveSubjectGroup(Context context) {
        this.context = context;
    }

    public void saveSubjectGroup(SubjectGroup subjectGroup) {
        Realm realm = Realm.getDefaultInstance();
        SubjectGroup subjectGroup_tmp = realm.where(SubjectGroup.class).
                equalTo("msv", subjectGroup.getMsv()).
                equalTo("subjectCode", subjectGroup.getSubjectCode()).findFirst();
        if (subjectGroup_tmp == null) {
            try {
                realm.beginTransaction();
                Log.w("SaveSubjectGroup", "Start save SG");
                Long count = realm.where(SubjectGroup.class).count();
                subjectGroup.setId(String.valueOf(count));
                realm.copyToRealm(subjectGroup);
                realm.commitTransaction();
//                addAlarm(subjectGroup.getRawExamDay(), subjectGroup);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                realm.close();
            }
        }else {
            try {
                realm.beginTransaction();
                Log.w("EditSubjectGroup", "Edit save SG");
                subjectGroup_tmp.setSbdUser(subjectGroup.getSbdUser());
                subjectGroup_tmp.setExamDay(subjectGroup.getExamDay());
                subjectGroup_tmp.setExamRoom(subjectGroup.getExamRoom());
                subjectGroup_tmp.setTypeExam(subjectGroup.getTypeExam());
                subjectGroup_tmp.setSubjectName(subjectGroup.getSubjectName());
                subjectGroup_tmp.setTerm(subjectGroup.getTerm());
                subjectGroup_tmp.setUpdate_on(subjectGroup.getUpdate_on());
                subjectGroup_tmp.setRawExamDay(subjectGroup.getRawExamDay());
                realm.commitTransaction();
                addAlarm(subjectGroup.getRawExamDay(), subjectGroup);
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
            Log.w("getAllSubjectGroup", "Get all Subject Group" + realm.where(SubjectGroup.class).count());
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
        return subjectGroupList;
    }

    public void deleteRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SubjectGroup> rows = realm.where(SubjectGroup.class).findAll();
                rows.deleteAllFromRealm();
                Log.w("delete", "SG");
            }
        });
        realm.close();
    }

    private void addAlarm(Long rawExamDay, SubjectGroup subjectGroup) {
        Long diff = Helper.subtractionDate(rawExamDay);
        Helper.startAlarm(context, 1, subjectGroup);
        if (diff <= 48 && diff > 0) {
            Helper.startAlarm(context, (int) (diff - 0), subjectGroup);
        } else if (diff > 48) {
            Helper.startAlarm(context, (int) (diff - 48), subjectGroup);
        }
    }
}
