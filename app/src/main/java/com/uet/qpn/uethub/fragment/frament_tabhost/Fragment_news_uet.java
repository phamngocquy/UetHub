package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.net.Uri;
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
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.rclViewAdapter.EndlessRecyclerViewScrollListener;
import com.uet.qpn.uethub.rclViewAdapter.RclNewsViewAdapter;
import com.uet.qpn.uethub.saveRealm.SaveNew;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import java.util.ArrayList;

public class Fragment_news_uet extends Fragment {

    private RclNewsViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
//    private EndlessRecyclerViewScrollListener scrollListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_uet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("init fragment_new_uet", "run in");
        init(view);
    }

    void init(View view) {

        swipeRefreshLayout = view.findViewById(R.id.refresh_uet_news);
        swipeRefreshLayout.setOnRefreshListener(swipe_refresh_uet_news);


        ArrayList<NewsEntity> mData = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.rclViewNewsUet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        /*scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                initData();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);*/

        adapter = new RclNewsViewAdapter(mData, getContext());
        recyclerView.setAdapter(adapter);
        initData();
    }

    public void initData() {
        String url = Configuration.HOST + Configuration.API_PATH_NEWS +
                "news=UET&page=" + Helper.getPageNumber(adapter.getItemCount());

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adapter.upDateData(Helper.getNewsEntity(response, "UET"));


                ArrayList<NewsEntity> newsEntities = adapter.getNewsEntities();
                SaveNew saveNew = new SaveNew();
                for (int i = 0; i < newsEntities.size(); i++) {
                    saveNew.saveNew(newsEntities.get(i));
                }
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

    SwipeRefreshLayout.OnRefreshListener swipe_refresh_uet_news = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            initData();
        }
    };

}
