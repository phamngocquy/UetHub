package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.annotation.SuppressLint;
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

public class Fragment_news_fit extends Fragment {


    @SuppressLint("StaticFieldLeak")
    public static Fragment_news_fit newsFit = null;

    private RclNewsViewAdapter adapter;


    /*public static Fragment_news_fit getInstance() {
        if (newsFit == null) {

            newsFit = new Fragment_news_fit();
        }
        return newsFit;
    }
*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.faragment_news_fit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("init fragment_new_fit", "run in");
        init(view);
    }

    void init(View view) {
        ArrayList<NewsEntity> mData = new ArrayList<>();
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
        if (savedInstanceState == null) Log.d("abc", "null");
    }

    public void initData() {

        String url = Configuration.API_PATH +
                "news=FIT&page=" + Helper.getPageNumber(adapter.getItemCount());

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("json", response);
                adapter.upDateData(Helper.getNewsEntity(response, "FIT"));
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
}
