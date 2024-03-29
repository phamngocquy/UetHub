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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.rclViewAdapter.RclExamResultViewAdapter;
import com.uet.qpn.uethub.saveRealm.SaveNew;
import com.uet.qpn.uethub.saveRealm.SaveSubject;
import com.uet.qpn.uethub.saveRealm.SaveUser;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import is.arontibo.library.ElasticDownloadView;

public class Fragment_noti_result extends Fragment {

    private RclExamResultViewAdapter adapter;
    private SaveUser saveUser = new SaveUser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView frg_result", "run in");
        return inflater.inflate(R.layout.fragment_noti_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view) {
        List<Subject> subjects = new ArrayList<>();
        SaveSubject saveSubject = new SaveSubject();
        subjects = saveSubject.getAllSubjects();
        RecyclerView recyclerView = view.findViewById(R.id.rclViewResult);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new RclExamResultViewAdapter(subjects, getContext());
        recyclerView.setAdapter(adapter);
        initData();
    }

    private void initData() {

        String url = Configuration.HOST + Configuration.API_PATH_EXAM_RESULT;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Json_Result: ", response);
                adapter.upDateData(Helper.getSubjectEntity_Result(response));
                ArrayList<Subject> subjects = (ArrayList<Subject>) adapter.getSubjects();
                SaveSubject saveSubject = new SaveSubject();
                for (int i = 0; i < subjects.size(); i++) {
                    saveSubject.saveSubject(subjects.get(i));
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
                params.put("msv", saveUser.getMSV());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}
