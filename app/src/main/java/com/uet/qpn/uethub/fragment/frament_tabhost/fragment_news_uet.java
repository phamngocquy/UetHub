package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uet.qpn.uethub.R;

public class fragment_news_uet extends Fragment {
    public static fragment_news_uet newsUet = null;

    public static fragment_news_uet getInstance() {
        if (newsUet == null) {
            Log.d("init fragment_new_uet","run in");
            newsUet = new fragment_news_uet();
        }
        return newsUet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_uet, container, false);
    }
}
