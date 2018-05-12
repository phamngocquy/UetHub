package com.uet.qpn.uethub.fragment.fragment_viewpager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.Switch;
import com.squareup.picasso.Picasso;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.SubjectGroup;
import com.uet.qpn.uethub.fcm.MyFirebaseInstanceIDService;
import com.uet.qpn.uethub.saveRealm.SaveSubjectGroup;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_setting extends Fragment {
    private CallbackManager callbackManager;
    private CircleImageView imgAvt;
    private TextView txtUserName, txtEmail;
    private MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
    private ProfileTracker profileTracker;

    private Switch fepnSwitch, fetSwitch, uetSwitch, resultSwitch, examSwitch, fitSwitch;


    public Fragment_setting() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken == null;
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Profile.setCurrentProfile(currentProfile);
            }
        };
        profileTracker.startTracking();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        AppEventsLogger.activateApp(getContext());
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        AppEventsLogger.activateApp(getContext());
        initReg();
        initSwitch(view);
        initBtnMsv(view);

        callbackManager = CallbackManager.Factory.create();

        imgAvt = view.findViewById(R.id.imgAvt);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtEmail = view.findViewById(R.id.txtEmail);

        LoginButton loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        // If using in a fragment
        loginButton.setFragment(this);


        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        examSwitch.setEnabled(true);
                        resultSwitch.setEnabled(true);

                        Profile profile = Profile.getCurrentProfile();
                        Uri uri = profile.getProfilePictureUri(150, 150);
                        Picasso.get().load(uri).into(imgAvt);
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {
                                    txtUserName.setText(object.getString("name"));
                                    String email = object.getString("email");
                                    txtEmail.setText(email);
                                    Log.d("email_", email);
                                    myFirebaseInstanceIDService.updateDeviceToken(email, myFirebaseInstanceIDService.getInstanceID());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken == null;
        boolean isExpired;
        if (!isLoggedIn) {
            isExpired = accessToken.isExpired();
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                final Uri uri = profile.getProfilePictureUri(150, 150);
                Picasso.get().load(uri).into(imgAvt);
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            txtUserName.setText(object.getString("name"));
                            txtEmail.setText(object.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initBtnMsv(View view) {
        ButtonFlat btnMsv = view.findViewById(R.id.btnEditMsv);
        final TextView txtMsv = view.findViewById(R.id.txtMsv);
        if(!checkLogin()) {
            txtMsv.setText(getMsvFromSharedPreference());
            btnMsv.setVisibility(View.VISIBLE);
        }else {
            txtMsv.setText("   Bạn cần đăng nhập để thay đổi MSV");
            btnMsv.setVisibility(View.GONE);
        }
        btnMsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialog_view = layoutInflater.inflate(R.layout.setting_dialog, null);
                final EditText edit_msv = dialog_view.findViewById(R.id.edtMsv);

                builder.setView(dialog_view);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // store to shared_preference
                        putMsvToSharedPreference(edit_msv);
                        txtMsv.setText(edit_msv.getText().toString());
                        dialog.dismiss();

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel edit
                        dialog.dismiss();
                    }
                });

                AlertDialog dialogEditMsv = builder.create();
                dialogEditMsv.getWindow().setBackgroundDrawableResource(R.drawable.dialog_setting);
                edit_msv.setText(getMsvFromSharedPreference());
                dialogEditMsv.show();
            }
        });


    }

    private void initSwitch(View view) {

        uetSwitch = view.findViewById(R.id.uetSwitch);
        fitSwitch = view.findViewById(R.id.fitSwitch);
        fepnSwitch = view.findViewById(R.id.fepnSwitch);
        fetSwitch = view.findViewById(R.id.fetSwitch);
        examSwitch = view.findViewById(R.id.uetExamSwitch);
        resultSwitch = view.findViewById(R.id.resultSwitch);


        uetSwitch.setOnTouchListener(clickUET);
        fitSwitch.setOnTouchListener(clickFIT);
        fetSwitch.setOnTouchListener(clickFET);
        fepnSwitch.setOnTouchListener(clickFEPN);
        examSwitch.setOnTouchListener(clickExam);
        resultSwitch.setOnTouchListener(clickResult);
    }

    Switch.OnTouchListener clickExam = new Switch.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(checkLogin()) {
                Toast.makeText(getContext(), "Vui lòng đăng nhập để Cài đặt thông báo lịch thi", Toast.LENGTH_LONG).show();
                examSwitch.setChecked(false);
            }
            else {
            initDataSwitch();
            }
            return false;
        }
    };


    Switch.OnTouchListener clickResult = new Switch.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(checkLogin()) {
                Toast.makeText(getContext(), "Vui lòng đăng nhập để Cài đặt thông báo kết quả", Toast.LENGTH_LONG).show();
                resultSwitch.setChecked(false);
            }
            else {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    initDataSwitch();
                }
            }
            return false;
        }
    };

    Switch.OnTouchListener clickUET = new Switch.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                initDataSwitch();
            }
            return false;
        }
    };

    Switch.OnTouchListener clickFIT = new Switch.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                initDataSwitch();
            }
            return false;
        }
    };

    Switch.OnTouchListener clickFET = new Switch.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                initDataSwitch();
            }
            return false;
        }
    };

    Switch.OnTouchListener clickFEPN = new Switch.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                initDataSwitch();
            }
            return false;
        }
    };


    private boolean checkLogin() {
        return AccessToken.getCurrentAccessToken() == null;
    }

    private void putMsvToSharedPreference(EditText edit_msv) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("msv", edit_msv.getText().toString());
        editor.apply();
    }

    private String getMsvFromSharedPreference() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("msv", null);
    }

//    private void initDataMSV(String msv) {
//
//        String url = Configuration.HOST + Configuration.API_PATH_UPDATE_MSV + "msv=" + Configuration.MSV;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("Json_Result: exam", response);
//                adapter.upDateData(Helper.getSubjectGroup(response));
//                ArrayList<SubjectGroup> subjectGroups = (ArrayList<SubjectGroup>) adapter.getSubjectGroups();
//                SaveSubjectGroup saveSubjectGroup = new SaveSubjectGroup();
//                Log.d("size", String.valueOf(subjectGroups.size()));
//                for (int i = 0; i < subjectGroups.size(); i++) {
//                    saveSubjectGroup.saveSubjectGroup(subjectGroups.get(i));
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Error_Frag_Ex_Result", error.toString());
//            }
//        }) ;
//        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
//    }

    private void initDataSwitch() {

        String url = Configuration.HOST + Configuration.API_PATH_UPDATE_NEW_SW ;
        Log.w("You are in init data", "really");
        if (AccessToken.getCurrentAccessToken() != null){
            // da dang nhap
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {

                    try {
                        Log.w("testttt", "sa");
                        String email = object.getString("email");
                        Log.d("email_", email);
                        String fcm =  myFirebaseInstanceIDService.getInstanceID();
                        String msv = getMsvFromSharedPreference();
                        if (msv == null){
                            msv = "";
                        }
                        String uet = String.valueOf(uetSwitch.isCheck());
                        String fit = String.valueOf(fitSwitch.isCheck());
                        String fet = String.valueOf(fetSwitch.isCheck());
                        String fepn = String.valueOf(fepnSwitch.isCheck());
                        String exam = String.valueOf(examSwitch.isCheck());
                        String grade = String.valueOf(resultSwitch.isCheck());
                        myFirebaseInstanceIDService.updateSW(email, fcm, msv, uet,fit, fet, fepn,exam, grade);
                        // gui du lieu len serrver o day
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();

        }else {
            // chua dang nhap


                    String email = "";
                    String fcm =  myFirebaseInstanceIDService.getInstanceID();
                    String msv = getMsvFromSharedPreference();
                    if (msv == null){
                        msv = "";
                    }
                    String uet = String.valueOf(uetSwitch.isCheck());
                    String fit = String.valueOf(fitSwitch.isCheck());
                    String fet = String.valueOf(fetSwitch.isCheck());
                    String fepn = String.valueOf(fepnSwitch.isCheck());
                    String exam = String.valueOf(examSwitch.isCheck());
                    String grade = String.valueOf(resultSwitch.isCheck());
                    myFirebaseInstanceIDService.updateSW(email, fcm, msv, uet,fit, fet, fepn,exam, grade);
                    // gui du lieu len serrver o day

            // gui du lieu len serrver o day
        }

    }

    private void initReg(){

        String url = Configuration.HOST + Configuration.API_PATH_GET_NEW_SW;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Json_Result", response);
                List<String> listOfReg = Helper.getReg(response);
                Log.d("Json_Result: exam", listOfReg.toString());
                updateSW(listOfReg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error_Frag_Ex_Result", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                String email = "";
                Log.w("login", "???");
                if (AccessToken.getCurrentAccessToken() != null){
                    // da dang nhap
                    email = txtEmail.getText().toString();
                    Log.w("login", "yes");
                }else {
                    // chua dang nhap
                    email = "";
                    Log.w("login", "no");
                }
                params.put("email", email);
                String fcm =  myFirebaseInstanceIDService.getInstanceID();
                    // gui du lieu len serrver o day=
                params.put("fcm", fcm);
                Log.w("login", email);
                Log.w("em00", "sssss");
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }
    private void updateSW(List<String> stringList){
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equals("UET")){
                uetSwitch.setChecked(true);
            } else if (stringList.get(i).equals("FIT")){
                fitSwitch.setChecked(true);
            } else if (stringList.get(i).equals("FET")){
                fetSwitch.setChecked(true);
            } else if (stringList.get(i).equals("FEPN")){
                fepnSwitch.setChecked(true);
            } else if (stringList.get(i).equals("EXAM")){
                examSwitch.setChecked(true);
            } else if (stringList.get(i).equals("GRADE")){
                resultSwitch.setChecked(true);
            }
        }
    }
}
