package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.uet.qpn.uethub.MainActivity;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.rclViewAdapter.RclNewsViewAdapter;
import com.uet.qpn.uethub.saveRealm.SaveNew;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import java.util.ArrayList;

public class Fragment_news_fit extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private RclNewsViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_fit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("init fragment_new_fit", "run in");
        init(view);
    }

    void init(View view) {
        swipeRefreshLayout = view.findViewById(R.id.refresh_fit_news);
        swipeRefreshLayout.setOnRefreshListener(swipe_refresh_fit_news);

        SaveNew saveNew = new SaveNew();
        ArrayList<NewsEntity> mData = saveNew.getNewsByNewsName("FIT");
        RecyclerView recyclerView = view.findViewById(R.id.rclViewNewsFit);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RclNewsViewAdapter(mData, getContext());
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            Log.d("savedInstanceState", "null");
        }
    }

    public void initData() {

        String url = Configuration.HOST + Configuration.API_PATH_NEWS +
                "news=FIT&page=" + Helper.getPageNumber(adapter.getItemCount());

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("json", response);
                ArrayList<NewsEntity> newsEntities = Helper.getNewsEntity(response,"FIT");
                SaveNew saveNew = new SaveNew();
                for (int i = 0; i < newsEntities.size(); i++) {
                    saveNew.saveNew(newsEntities.get(i));
                }
                adapter.upDateData(saveNew.getNewsByNewsName("FIT"));
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("data", adapter.getNewsEntities());
    }

    SwipeRefreshLayout.OnRefreshListener swipe_refresh_fit_news = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            initData();
        }
    };

}
