package com.uet.qpn.uethub.fragment.frament_tabhost;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uet.qpn.uethub.R;

public class fragment_news_fet extends Fragment {
    private static fragment_news_fet newsFet = null;

    public static fragment_news_fet getInstance() {
        if (newsFet == null) {
            newsFet = new fragment_news_fet();
        }
        return newsFet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_fet, container, false);
    }
}
