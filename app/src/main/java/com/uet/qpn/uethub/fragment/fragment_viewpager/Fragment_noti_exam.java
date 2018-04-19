package com.uet.qpn.uethub.fragment.fragment_viewpager;


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

import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.rclViewAdapter.RclExamResultViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment_noti_exam extends Fragment {


    public Fragment_noti_exam() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView frag__exam", "run in");
        return inflater.inflate(R.layout.fragment_noti_exam, container, false);
    }


}
