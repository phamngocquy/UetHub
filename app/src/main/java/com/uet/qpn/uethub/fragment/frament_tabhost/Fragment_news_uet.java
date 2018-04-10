package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.rclViewAdapter.RclNewsViewAdapter;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import java.util.ArrayList;

public class Fragment_news_uet extends Fragment {

    public static Fragment_news_uet newsUet = null;

    private RclNewsViewAdapter adapter;

    public static Fragment_news_uet getInstance() {
        if (newsUet == null) {
            Log.d("init fragment_new_uet", "run in");
            newsUet = new Fragment_news_uet();
        }
        return newsUet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_uet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    void init(View view) {
        ArrayList<NewsEntity> mData = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.rclViewNewsUet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RclNewsViewAdapter(mData, getContext());
        recyclerView.setAdapter(adapter);
        initData();
    }

    public void initData() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(Configuration.SERVER_HOST)
                .appendPath("api")
                .appendPath("v1")
                .appendPath("news")
                .appendPath("getEntitiesByNews")
                .appendQueryParameter("news", "UET")
                .appendQueryParameter("page", "0");
        StringRequest stringRequest = new StringRequest(builder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adapter.upDateData(Helper.getNewsEntity(response,"UET"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
