package com.uet.qpn.uethub.saveRealm;

import android.util.Log;

import com.uet.qpn.uethub.entity.NewsEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public List<NewsEntity> getAllNews() {
        Realm realm = Realm.getDefaultInstance();
        List<NewsEntity> newsEntities = new ArrayList<>();
        try {
            Log.w("nghia","get all of new");
            RealmResults<NewsEntity> entities = realm.where(NewsEntity.class).findAll();
            Log.w("count", String.valueOf(realm.where(NewsEntity.class).count()));
            Iterator<NewsEntity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                NewsEntity newsEntity = iterator.next();
                newsEntities.add(newsEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
        Collections.sort( newsEntities);
        Log.w("size", String.valueOf(newsEntities.size()));
        for (int i = 0; i < newsEntities.size(); i ++){
            Log.w("enti", newsEntities.get(i).getPublictime());
        }
        return newsEntities;
    }

//    public int getMaxID(){
//        Realm realm = Realm.getDefaultInstance();
//    }
}
