package com.uet.qpn.uethub.fragment.fragment_viewpager;


import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.entity.SubjectGroup;
import com.uet.qpn.uethub.rclViewAdapter.RclExamResultViewAdapter;
import com.uet.qpn.uethub.rclViewAdapter.RclExamViewAdapter;
import com.uet.qpn.uethub.saveRealm.SaveSubject;
import com.uet.qpn.uethub.saveRealm.SaveSubjectGroup;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_noti_exam extends Fragment {

    private RclExamViewAdapter adapter;

    //    List<Exam>
    public Fragment_noti_exam() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView frag__exam", "run in");
        return inflater.inflate(R.layout.fragment_noti_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view) {
        List<SubjectGroup> subjectGroups = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.rclViewExam);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new RclExamViewAdapter(subjectGroups, getContext());
        recyclerView.setAdapter(adapter);
        initData();
    }

    private void initData() {

        String url = Configuration.HOST + Configuration.API_PATH_EXAM;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Json_Result: exam", response);
                adapter.upDateData(Helper.getSubjectGroup(response));
                ArrayList<SubjectGroup> subjectGroups = (ArrayList<SubjectGroup>) adapter.getSubjectGroups();
                SaveSubjectGroup saveSubjectGroup = new SaveSubjectGroup();
                Log.d("size", String.valueOf(subjectGroups.size()));
                for (int i = 0; i < subjectGroups.size(); i++) {
                    saveSubjectGroup.saveSubjectGroup(subjectGroups.get(i));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error_Frag_Ex_Result", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("msv", getMsvFromSharedPreference());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private String getMsvFromSharedPreference() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("msv", null);
    }


}
