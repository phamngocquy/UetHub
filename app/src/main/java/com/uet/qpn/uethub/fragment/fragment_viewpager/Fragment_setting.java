package com.uet.qpn.uethub.fragment.fragment_viewpager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.fcm.MyFirebaseInstanceIDService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_setting extends Fragment {
    private CallbackManager callbackManager;
    private CircleImageView imgAvt;
    private TextView txtUserName;
    private MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
    ProfileTracker profileTracker;


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

        callbackManager = CallbackManager.Factory.create();

        imgAvt = view.findViewById(R.id.imgAvt);
        txtUserName = view.findViewById(R.id.txtUserName);

        LoginButton loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {



                   //     final Uri uri = profile.getProfilePictureUri(150, 150);
                    //    Picasso.get().load(uri).into(imgAvt);
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {
                                    txtUserName.setText(object.getString("name"));
                                    String email = object.getString("email");
                                    Log.d("emaillll", email);
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
}
