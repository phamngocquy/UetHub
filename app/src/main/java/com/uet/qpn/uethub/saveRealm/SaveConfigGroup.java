package com.uet.qpn.uethub.saveRealm;

import android.util.Log;

import com.uet.qpn.uethub.entity.ConfigGroup;
import com.uet.qpn.uethub.entity.NewsReg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SaveConfigGroup {

    public void saveConfigGroup(ConfigGroup configGroup) {
        Realm realm = Realm.getDefaultInstance();
        ConfigGroup configGroup_tmp = realm.where(ConfigGroup.class).equalTo("type", configGroup.getType()).findFirst();
        Log.w("nghia", "start save config " + configGroup.getType() );
        if (configGroup_tmp == null) {
            try {
                realm.beginTransaction();
                realm.copyToRealm(configGroup);
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
                configGroup_tmp.setType(configGroup.getType());
                configGroup_tmp.setValue(configGroup.getValue());
                realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                realm.close();
            }
        }
    }
    public String getValueByType(String type) {
        Realm realm = Realm.getDefaultInstance();
        String value = "";
        try {
            Log.w("nghia","get value by " + type);
            ConfigGroup configGroups = realm.where(ConfigGroup.class).equalTo("type", type).findFirst();
            if (configGroups != null) {
                value = configGroups.getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
        Log.w("value", value);
        return value;
    }
}
