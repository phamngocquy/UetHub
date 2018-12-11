package com.uet.qpn.uethub.saveRealm;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.NewsReg;
import com.uet.qpn.uethub.entity.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SaveUser {
    public void saveMSV(String msv) {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("id", Configuration.USER_KEY).findFirst();

        if (msv != null) {
            if (user == null) {
                try {
                    realm.beginTransaction();
                    realm.copyToRealm(new User(msv));
                    realm.commitTransaction();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Log.d("savemsv: ", msv);
                    realm.close();
                }
            } else {
                try {
                    realm.beginTransaction();
                    user.setMsv(msv);
                    realm.commitTransaction();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Log.d("savemsv: ", msv);
                    realm.close();
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getMSV() {
        String msv;
        try (Realm realm = Realm.getDefaultInstance()) {
            User user = realm.where(User.class).equalTo("id", Configuration.USER_KEY).findFirst();
            if (user == null || user.getMsv() == null) {
                return null;
            }
            msv = user.getMsv();
            return msv;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
