package com.uet.qpn.uethub.saveRealm;

import android.util.Log;

import com.uet.qpn.uethub.entity.Form;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmResults;

public class SaveForm {

    public void saveAll(ArrayList<Form> forms) {
        Realm realm = Realm.getDefaultInstance();
        try {
            for (Form form : forms) {
                Form form_tmp = realm.where(Form.class).equalTo("name", form.getName()).findFirst();
                if (form_tmp == null) {
                    realm.beginTransaction();
                    Long count = realm.where(Form.class).count();
                    Log.d("saveAllForm", "start save subject: " + count);
                    form.setId(String.valueOf(count));
                    realm.copyToRealm(form);
                    realm.commitTransaction();
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        } finally {
            realm.close();
        }
    }

    public ArrayList<Form> getAllForm() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Form> formlist = new ArrayList<>();
        RealmResults<Form> forms = realm.where(Form.class).findAll();
        formlist.addAll(forms);
        Collections.sort( formlist);
        return formlist;
    }
}
