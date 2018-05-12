package com.uet.qpn.uethub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginButton loginButton;

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
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    Log.d("fuck", object.getString("email"));
                    updateTokenAndEmailOfUser(object.getString("email"));
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
        startActivity(new Intent(this, MainActivity.class));
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
                Log.d("1234", email);
                Log.d("1234", FirebaseInstanceId.getInstance().getToken());
                Log.d("1234", getMsvFromSharedPreference());
                params.put("email", email);
                params.put("fcm", FirebaseInstanceId.getInstance().getToken());
                params.put("msv", getMsvFromSharedPreference());
                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private String getMsvFromSharedPreference() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("msv", "0");
    }
}
