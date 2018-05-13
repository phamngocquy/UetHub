package com.uet.qpn.uethub.saveRealm;

import android.util.Log;

import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.entity.NewsReg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SaveNewsReg {

    public void saveNewsReg(NewsReg newsReg) {
        Realm realm = Realm.getDefaultInstance();
        NewsReg reg = realm.where(NewsReg.class).equalTo("newsName", newsReg.getNewsName()).findFirst();
        Log.w("nghia", "start save reg " + newsReg.getNewsName() );
        if (reg == null) {
            try {
                realm.beginTransaction();
                realm.copyToRealm(newsReg);
                realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                realm.close();
            }
        }
        else {
            try {
                realm.beginTransaction();
                reg.setChecked(newsReg.getChecked());
                realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                realm.close();
            }
        }
    }
    public List<NewsReg> getAllNewsReg() {
        Realm realm = Realm.getDefaultInstance();
        List<NewsReg> newsRegs = new ArrayList<>();
        try {
            Log.w("nghia","get all of new reg");
            RealmResults<NewsReg> regs = realm.where(NewsReg.class).findAll();
            Iterator<NewsReg> iterator = regs.iterator();
            while (iterator.hasNext()) {
                NewsReg reg = iterator.next();
                NewsReg reg_tmp = new NewsReg();
                reg_tmp.setNewsName(reg.getNewsName());
                reg_tmp.setChecked(reg.getChecked());
                newsRegs.add(reg_tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
        for (int i = 0; i < newsRegs.size(); i++){
            Log.w(newsRegs.get(i).getNewsName(), newsRegs.get(i).getChecked().toString());
        }
        return newsRegs;
    }
}
