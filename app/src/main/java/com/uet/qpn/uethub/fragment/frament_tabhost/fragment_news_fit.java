package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.annotation.SuppressLint;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.rclViewAdapter.RclNewsViewAdapter;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import java.util.ArrayList;

public class fragment_news_fit extends Fragment {


    @SuppressLint("StaticFieldLeak")
    public static fragment_news_fit newsFit = null;

    private RclNewsViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    public static fragment_news_fit getInstance() {
        if (newsFit == null) {
            Log.d("init fragment_new_fit", "run in");
            newsFit = new fragment_news_fit();
        }
        return newsFit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.faragment_news_fit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    void init(View view) {
        ArrayList<NewsEntity> newsEntities = new ArrayList<>();
        newsEntities.add(new NewsEntity("a", "A", "a", "a", "a"));
        newsEntities.add(new NewsEntity("a", "A", "a", "a", "a"));
        recyclerView = view.findViewById(R.id.rclViewNewsFit);
        if (recyclerView == null) {
            Log.d("xx", "xx");
        }
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RclNewsViewAdapter(newsEntities, getContext());
        recyclerView.setAdapter(adapter);

        String url = "https://jsonplaceholder.typicode.com/albums";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                NewsEntity entity = new NewsEntity("a","A","a","A","B");
                adapter.addItem(entity);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("xxx", "error");
            }
        });


        RequestQueue requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        requestQueue.add(stringRequest);
    }

}
