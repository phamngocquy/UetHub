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
        if (entity == null) {
            try {
                realm.beginTransaction();
                Long count = realm.where(NewsEntity.class).count();
                Log.w("SaveNew", "start save new " + count);
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
            Log.w("getAllNews", "get all of new");
            RealmResults<NewsEntity> entities = realm.where(NewsEntity.class).findAll();
            Log.w("count", String.valueOf(realm.where(NewsEntity.class).count()));
            Iterator<NewsEntity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                NewsEntity entity = iterator.next();
                NewsEntity newsEntity = new NewsEntity();
                newsEntity.setTitle(entity.getTitle());
                newsEntity.setDescription(entity.getDescription());
                newsEntity.setCategories(entity.getCategories());
                newsEntity.setPublictime(entity.getPublictime());
                newsEntity.setAuthor(entity.getAuthor());
                newsEntity.setUrl(entity.getUrl());
                newsEntity.setNewsName(entity.getNewsName());
                newsEntities.add(newsEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Collections.sort(newsEntities);
            realm.close();
        }
        return newsEntities;
    }

    public ArrayList<NewsEntity> getNewsByNewsName(String newsName) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<NewsEntity> newsEntities = new ArrayList<>();
        try {
            Log.w("getNewsByNewsName", "get news by newsName");
            RealmResults<NewsEntity> entities = realm.where(NewsEntity.class).findAll();
            Log.w("count", String.valueOf(realm.where(NewsEntity.class).count()));
            Iterator<NewsEntity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                NewsEntity entity = iterator.next();
                if (entity.getNewsName().equals(newsName)) {
                    NewsEntity newsEntity = new NewsEntity();
                    newsEntity.setTitle(entity.getTitle());
                    newsEntity.setDescription(entity.getDescription());
                    newsEntity.setCategories(entity.getCategories());
                    newsEntity.setPublictime(entity.getPublictime());
                    newsEntity.setAuthor(entity.getAuthor());
                    newsEntity.setUrl(entity.getUrl());
                    newsEntity.setNewsName(entity.getNewsName());
                    newsEntities.add(newsEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Collections.sort(newsEntities);
            Log.w("size", String.valueOf(newsEntities.size()));
            if (realm != null && !realm.isClosed()) {
                realm.close();
            }
        }
        return newsEntities;
    }


}
