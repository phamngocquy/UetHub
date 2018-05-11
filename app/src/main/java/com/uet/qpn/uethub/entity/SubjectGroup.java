package com.uet.qpn.uethub.entity;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SubjectGroup extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;

    private String msv;

    private String sbdUser;

    // ngay thi
    private String examDay;

    // phong thi
    private String examRoom;

    // kieu thi viet hay thuc hanh ..
    private String typeExam;

    private String subjectName;

    private String subjectCode;

    private String term;

    private String update_on;

    public SubjectGroup() {
    }

    public SubjectGroup(String id, String msv, String sbdUser, String examDay, String examRoom, String typeExam, String subjectName, String subjectCode, String term, String update_on) {
        this.id = id;
        this.msv = msv;
        this.sbdUser = sbdUser;
        this.examDay = examDay;
        this.examRoom = examRoom;
        this.typeExam = typeExam;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.term = term;
        this.update_on = update_on;
    }

    public SubjectGroup(String id, String msv, String sbdUser, String examDay, String examRoom, String typeExam, String subjectName, String subjectCode) {
        this.id = id;
        this.msv = msv;
        this.sbdUser = sbdUser;
        this.examDay = examDay;
        this.examRoom = examRoom;
        this.typeExam = typeExam;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

    public String getSbdUser() {
        return sbdUser;
    }

    public void setSbdUser(String sbdUser) {
        this.sbdUser = sbdUser;
    }

    public String getExamDay() {
        return examDay;
    }

    public void setExamDay(String examDay) {
        this.examDay = examDay;
    }

    public String getExamRoom() {
        return examRoom;
    }

    public void setExamRoom(String examRoom) {
        this.examRoom = examRoom;
    }

    public String getTypeExam() {
        return typeExam;
    }

    public void setTypeExam(String typeExam) {
        this.typeExam = typeExam;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getUpdate_on() {
        return update_on;
    }

    public void setUpdate_on(String update_on) {
        this.update_on = update_on;
    }
}
