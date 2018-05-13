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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.Form;
import com.uet.qpn.uethub.rclViewAdapter.RclFormViewAdapter;
import com.uet.qpn.uethub.saveRealm.SaveForm;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_form extends Fragment {

    private RclFormViewAdapter rclFormViewAdapter;

    public Fragment_form() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView frag__form", "run in");
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("onCreateView frag__form", "run in");
        init(view);

    }

    private void init(View view) {
        SaveForm saveForm = new SaveForm();
        ArrayList<Form> forms;
        forms = saveForm.getAllForm();
        RecyclerView rclViewForm = view.findViewById(R.id.rclViewForm);
        rclFormViewAdapter = new RclFormViewAdapter(getContext(), forms);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rclViewForm.setLayoutManager(layoutManager);
        rclViewForm.setAdapter(rclFormViewAdapter);

        initData();
    }

    private void initData() {
        String url = Configuration.HOST + Configuration.API_PATH_FORM;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("form_data: ", response);
                SaveForm saveForm = new SaveForm();
                saveForm.saveAll(Helper.getForm(response));
                rclFormViewAdapter.updateData(saveForm.getAllForm());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
