package com.uet.qpn.uethub.fragment.fragment_viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uet.qpn.uethub.R;

public class fragment_noti_result extends Fragment {

    public fragment_noti_result() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView frg_result","run in");
        return inflater.inflate(R.layout.fragment_noti_result, container, false);
    }
}