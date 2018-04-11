package com.uet.qpn.uethub.fragment.fragment_viewpager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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
import com.uet.qpn.uethub.fragment.adapter.PagerAdapter;
import com.uet.qpn.uethub.rclViewAdapter.RclNewsViewAdapter;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import java.util.ArrayList;

public class Fragment_news extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public Fragment_news() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        FragmentManager fragmentManager = getFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
