package com.uet.qpn.uethub.fragment.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.uet.qpn.uethub.fragment.frament_tabhost.Fragment_news_fepn;
import com.uet.qpn.uethub.fragment.frament_tabhost.Fragment_news_fet;
import com.uet.qpn.uethub.fragment.frament_tabhost.Fragment_news_fit;
import com.uet.qpn.uethub.fragment.frament_tabhost.Fragment_news_uet;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = Fragment_news_uet.getInstance();
                break;
            case 1:
                fragment = Fragment_news_fit.getInstance();
                break;
            case 2:
                fragment = Fragment_news_fet.getInstance();
                break;
            case 3:
                fragment = Fragment_news_fepn.getInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "UET";
                break;
            case 1:
                title = "FIT";
                break;
            case 2:
                title = "FET";
                break;
            case 3:
                title = "FEPN";
                break;
        }
        return title;
    }
}
