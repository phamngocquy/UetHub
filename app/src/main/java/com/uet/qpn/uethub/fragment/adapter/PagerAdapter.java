package com.uet.qpn.uethub.fragment.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.uet.qpn.uethub.fragment.frament_tabhost.fragment_news_fet;
import com.uet.qpn.uethub.fragment.frament_tabhost.fragment_news_fit;
import com.uet.qpn.uethub.fragment.frament_tabhost.fragment_news_uet;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = fragment_news_uet.getInstance();
                break;
            case 1:
                fragment = fragment_news_fit.getInstance();
                break;
            case 2:
                fragment = fragment_news_fet.getInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Uet";
                break;
            case 1:
                title = "Fit";
                break;
            case 2:
                title = "Fet";
                break;
        }
        return title;
    }
}
