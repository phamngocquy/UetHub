package com.uet.qpn.uethub.saveRealm;

import android.util.Log;

import com.uet.qpn.uethub.entity.NewsEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SaveNew {
    public void saveNew(NewsEntity newsEntity) {
        Realm realm = Realm.getDefaultInstance();
        NewsEntity entity = realm.where(NewsEntity.class).equalTo("url", newsEntity.getUrl()).findFirst();
        Log.w("why", "w");
        if (entity == null) {
            try {
                    realm.beginTransaction();
                    Long count = realm.where(NewsEntity.class).count();
                    Log.w("nghia", "start save new " + count);
                    newsEntity.setId(String.valueOf(count));
                    realm.copyToRealm(newsEntity);
                    realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                realm.close();
            }
        }
    }
    public void getAllNew() {
        Realm realm = Realm.getDefaultInstance();
        try {
            Log.w("nghia","get all of new");
            RealmResults<NewsEntity> entities = realm.where(NewsEntity.class).findAll();
            Log.w("count", String.valueOf(realm.where(NewsEntity.class).count()));
            List<NewsEntity> newsEntities = new ArrayList<>();
            Iterator<NewsEntity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                NewsEntity newsEntity = iterator.next();
                Log.w("title", newsEntity.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }

//    public int getMaxID(){
//        Realm realm = Realm.getDefaultInstance();
//    }
}
