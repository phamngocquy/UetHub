package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uet.qpn.uethub.R;

public class fragment_news_fit extends Fragment {

    public static fragment_news_fit newsFit = null;

    public static fragment_news_fit getInstance() {
        if (newsFit == null) {
            newsFit = new fragment_news_fit();
        }
        return newsFit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.faragment_news_fit, container, false);
    }
}
