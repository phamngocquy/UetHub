package com.uet.qpn.uethub;

import android.util.Log;

import com.uet.qpn.uethub.entity.NewsEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Helper {

    public static ArrayList<NewsEntity> getNewsEntity(String response) {
        ArrayList<NewsEntity> entities = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                NewsEntity entity = new NewsEntity();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                entity.setAuthor(jsonObject.getString("author"));
                entity.setCategories(jsonObject.getString("categories"));
                entity.setDescription(jsonObject.getString("description"));
                entity.setTitle(jsonObject.getString("title"));

                Long dataNum = Long.valueOf(jsonObject.getString("date"));
                Date  date = new Date(dataNum);
                entity.setPublictime(date.toString());


                Log.d("xxx",entity.toString());
                entities.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return entities;
    }
}
