package com.uet.qpn.uethub.fragment.fragment_viewpager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.Switch;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.LoginActivity;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.NewsReg;
import com.uet.qpn.uethub.fcm.MyFirebaseInstanceIDService;
import com.uet.qpn.uethub.saveRealm.SaveNew;
import com.uet.qpn.uethub.saveRealm.SaveNewsReg;
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
    private Switch fepnSwitch, fetSwitch, uetSwitch, resultSwitch, examSwitch, fitSwitch;


    public Fragment_setting() {
        ProfileTracker profileTracker = new ProfileTracker() {
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
        initSwitch(view);
        initBtnMsv(view);
        //loadConfig();


        ButtonFloat btnLogout = view.findViewById(R.id.btnLogout);
        ButtonFloat btnUpdate = view.findViewById(R.id.btnUpdate);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prelogoutAcc();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNewRegister();
            }
        });
        callbackManager = CallbackManager.Factory.create();
        imgAvt = view.findViewById(R.id.imgAvt);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtEmail = view.findViewById(R.id.txtEmail);

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
        if (!isLoggedIn) {
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
                            loadConfig();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
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
        txtMsv.setText(getMsvFromSharedPreference());

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
    }

    private void putMsvToSharedPreference(EditText edit_msv) {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("msv", edit_msv.getText().toString());
        editor.apply();
    }

    private String getMsvFromSharedPreference() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("msv", "");
    }


    private void updateNewRegister() {

        String url = Configuration.HOST + Configuration.API_PATH_UPDATE_MSV;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("msv", getMsvFromSharedPreference());
                params.put("email", txtEmail.getText().toString());
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


        try {
            JSONObject rootObject = new JSONObject();
            JSONObject property = new JSONObject();

            property.put("uet", uetSwitch.isCheck());
            property.put("fit", fitSwitch.isCheck());
            property.put("fet", fetSwitch.isCheck());
            property.put("fepn", fepnSwitch.isCheck());
            property.put("exam", examSwitch.isCheck());
            property.put("grade", resultSwitch.isCheck());
            rootObject.put("email", txtEmail.getText().toString());
            rootObject.put("fcm", FirebaseInstanceId.getInstance().getToken());
            rootObject.put("msv", getMsvFromSharedPreference());
            rootObject.put("property", property);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Configuration.HOST + Configuration.APT_PATH_UPDATE_NEW_REGISTER, rootObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Boolean result = (Boolean) response.get("result");
                        if (result == true){
                            SaveNewsReg saveNewsReg = new SaveNewsReg();
                            saveNewsReg.saveNewsReg(new NewsReg("UET", uetSwitch.isCheck()));
                            saveNewsReg.saveNewsReg(new NewsReg("FIT", fitSwitch.isCheck()));
                            saveNewsReg.saveNewsReg(new NewsReg("FET", fetSwitch.isCheck()));
                            saveNewsReg.saveNewsReg(new NewsReg("FEPN", fepnSwitch.isCheck()));
                            saveNewsReg.saveNewsReg(new NewsReg("EXAM", examSwitch.isCheck()));
                            saveNewsReg.saveNewsReg(new NewsReg("RESULT", resultSwitch.isCheck()));
                            Toast.makeText(getContext(),"Cập nhật thành công!", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),"Cập nhật thất bại!", Toast.LENGTH_LONG).show();
                }
            });
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void prelogoutAcc() {
        unRegNoti();

    }

    private void logout() {
        LoginManager.getInstance().logOut();
        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

    private void unRegNoti() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken == null;
        if (!isLoggedIn) {

            final GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {

                    try {
                        MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService();

                        String email = object.getString("email");
                        String fcm = myFirebaseInstanceIDService.getInstanceID();

                        String url = Configuration.HOST + Configuration.API_PATH_REMOVE_FCM;
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("true")) {
                                    // da xoa thong tin thanh cong
                                    logout();

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("email", txtEmail.getText().toString());
                                params.put("fcm", FirebaseInstanceId.getInstance().getToken());
                                return params;
                            }
                        };

                        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    private void loadConfig(){
        String url = Configuration.HOST + Configuration.API_PATH_GET_NEW_SW;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<String> listOfReg = Helper.getReg(response);
                updateSW(listOfReg);
                allowChangeSW(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SaveNewsReg saveNewsReg = new SaveNewsReg();
                List<NewsReg> newsRegs = saveNewsReg.getAllNewsReg();
                List<String> stringList = new ArrayList<>();
                for (int i = 0; i <  newsRegs.size(); i++){
                    if(newsRegs.get(i).getChecked()){
                        stringList.add(newsRegs.get(i).getNewsName());
                    }
                }
                updateSW(stringList);
                allowChangeSW(false);
                Toast.makeText(getContext(),"Vui lòng kiểm tra lại đường truyền!", Toast.LENGTH_LONG).show();
                Log.d("Error_Frag_Ex_Result", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                String email = txtEmail.getText().toString();
                params.put("email", email);
                String fcm =  FirebaseInstanceId.getInstance().getToken();
                params.put("fcm", fcm);
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
            } else if (stringList.get(i).equals("GRADE") || stringList.get(i).equals("RESULT")){
                resultSwitch.setChecked(true);
            }
        }
    }

    private void allowChangeSW(Boolean connect){
        uetSwitch.setEnabled(connect);
        fitSwitch.setEnabled(connect);
        fetSwitch.setEnabled(connect);
        fepnSwitch.setEnabled(connect);
        examSwitch.setEnabled(connect);
        resultSwitch.setEnabled(connect);
    }
}
