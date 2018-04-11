package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.os.Bundle;
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

public class Fragment_news_fepn extends Fragment {

    private RclNewsViewAdapter adapter;

    public static Fragment_news_fepn newsFepn = null;

   /* public static Fragment_news_fepn getInstance() {
        if (newsFepn == null) {
            Log.d("init fragment_new_fepn", "run in");
            newsFepn = new Fragment_news_fepn();
        }
        return newsFepn;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_fepn, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("init fragment_new_fepn", "run in");
        init(view);

    }

    void init(View view) {
        ArrayList<NewsEntity> mData = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.rclViewNewsFepn);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RclNewsViewAdapter(mData, getContext());
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initData() {
        String url = Configuration.API_PATH +
                "news=FEPN&page=" + Helper.getPageNumber(adapter.getItemCount());
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adapter.upDateData(Helper.getNewsEntity(response, "FEPN"));
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
