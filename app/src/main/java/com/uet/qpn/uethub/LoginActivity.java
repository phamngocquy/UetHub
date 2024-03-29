package com.uet.qpn.uethub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.NewsReg;
import com.uet.qpn.uethub.saveRealm.SaveNewsReg;
import com.uet.qpn.uethub.saveRealm.SaveUser;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private SaveUser saveUser = new SaveUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);


        loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();


        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");


        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) nextScreen(accessToken);
        // If using in a fragment

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code


                nextScreen(loginResult.getAccessToken());
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


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        nextScreen(loginResult.getAccessToken());
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void nextScreen(AccessToken accessToken) {

        try {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        String email = object.getString("email");

                        if (email != null) {
                            try {
                                getAndStoreMSV(email);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                getAndStoreNewsReg(email);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        startActivity(new Intent(this, MainActivity.class));
    }

    private void getAndStoreNewsReg(final String email) {
        // doc tu serrver roi luu vao local
        // dung api nao day
        // t nho m code r
        // copy sang, go lai lam chi
        String url = Configuration.HOST + Configuration.API_PATH_GET_NEW_SW;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<String> stringList = Helper.getReg(response);
                // m code luu o day
                // oke
                // that oke
                // de t test nha
                // cho mot ty
                // dung co tat teamview
                SaveNewsReg saveNewsReg = new SaveNewsReg();
                List<NewsReg> newsRegList = saveNewsReg.getAllNewsReg();
                for (int i = 0; i < newsRegList.size(); i++) {
                    newsRegList.get(i).setChecked(false);
                    saveNewsReg.saveNewsReg(newsRegList.get(i));
                }
                for (int i = 0; i < stringList.size(); i++) {
                    NewsReg newsReg = new NewsReg();
                    if (stringList.get(i).equals("UET")) {
                        newsReg.setNewsName("UET");
                        newsReg.setChecked(true);
                    } else if (stringList.get(i).equals("FIT")) {
                        newsReg.setNewsName("FIT");
                        newsReg.setChecked(true);
                    } else if (stringList.get(i).equals("FET")) {
                        newsReg.setNewsName("FET");
                        newsReg.setChecked(true);
                    } else if (stringList.get(i).equals("FEPN")) {
                        newsReg.setNewsName("FEPN");
                        newsReg.setChecked(true);
                    } else if (stringList.get(i).equals("EXAM")) {
                        newsReg.setNewsName("EXAM");
                        newsReg.setChecked(true);
                    } else if (stringList.get(i).equals("GRADE")) {
                        newsReg.setNewsName("GRADE");
                        newsReg.setChecked(true);
                    }
                    saveNewsReg.saveNewsReg(newsReg);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("email", email);
                String fcm = FirebaseInstanceId.getInstance().getToken();
                params.put("fcm", fcm);
                return params;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    public void updateTokenAndEmailOfUser(final String email) {
        Log.d("updateToken", "cap nhat token");
        String url = Configuration.HOST + Configuration.API_PATH_GET_CREATE_USER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Result: ", response);
                if (response.equals("true")) {
                    Log.d("updatefcm", "oke");
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

                String firebaseID = FirebaseInstanceId.getInstance().getToken();
                if (firebaseID == null) {
                    firebaseID = "";
                }
                params.put("email", email);
                params.put("fcm", firebaseID);
                params.put("msv", saveUser.getMSV());
                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    public void getAndStoreMSV(final String email) {
        String url = Configuration.HOST + Configuration.API_PATH_GET_CONFIG;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("object: " + jsonObject.toString());
                    String msv = jsonObject.getString("msv");
                    if (msv != null && !msv.equals("") && !msv.equals("null")) {

                        if (!msv.equals(saveUser.getMSV())) {
                            saveUser.saveMSV(msv);
                        }
                    }
                    updateTokenAndEmailOfUser(email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Vui lòng kiểm tra lại đường truyền!", Toast.LENGTH_LONG).show();
                Log.d("Error_Frag_Ex_Result", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("email", email);
                String fcm = FirebaseInstanceId.getInstance().getToken();
                params.put("fcm", fcm);
                return params;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }
}
