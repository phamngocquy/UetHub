package com.uet.qpn.uethub.fragment.fragment_viewpager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.gc.materialdesign.views.Switch;
import com.squareup.picasso.Picasso;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.fcm.MyFirebaseInstanceIDService;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_setting extends Fragment {
    private CallbackManager callbackManager;
    private CircleImageView imgAvt;
    private TextView txtUserName;
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
        initSwitch(view);
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        AppEventsLogger.activateApp(getContext());
        initSwitch(view);

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


    private void initSwitch(View view) {

        uetSwitch = view.findViewById(R.id.uetSwitch);
        fitSwitch = view.findViewById(R.id.fitSwitch);
        fepnSwitch = view.findViewById(R.id.fepnSwitch);
        fetSwitch = view.findViewById(R.id.fetSwitch);
        examSwitch = view.findViewById(R.id.uetExamSwitch);
        resultSwitch = view.findViewById(R.id.resultSwitch);


        uetSwitch.setOncheckListener(changeUetSwitch);
        fitSwitch.setOncheckListener(changeFitSwitch);
        fepnSwitch.setOncheckListener(changeFepnSwitch);

        examSwitch.setOncheckListener(changeExamSwitch);
        resultSwitch.setOncheckListener(changeResultSwitch);
        fetSwitch.setOncheckListener(changeFetSwitch);

    }

    Switch.OnCheckListener changeUetSwitch = new Switch.OnCheckListener() {
        @Override
        public void onCheck(Switch view, boolean check) {

        }
    };

    Switch.OnCheckListener changeFitSwitch = new Switch.OnCheckListener() {
        @Override
        public void onCheck(Switch view, boolean check) {

        }
    };

    Switch.OnCheckListener changeFepnSwitch = new Switch.OnCheckListener() {
        @Override
        public void onCheck(Switch view, boolean check) {

        }
    };

    Switch.OnCheckListener changeFetSwitch = new Switch.OnCheckListener() {
        @Override
        public void onCheck(Switch view, boolean check) {


        }
    };

    Switch.OnCheckListener changeExamSwitch = new Switch.OnCheckListener() {
        @Override
        public void onCheck(Switch view, boolean check) {
            if (checkLogin()) {
                Toast.makeText(getContext(), "Can dang nhap de su dung tinh nang nay", Toast.LENGTH_LONG).show();
            }
        }
    };

    Switch.OnCheckListener changeResultSwitch = new Switch.OnCheckListener() {
        @Override
        public void onCheck(Switch view, boolean check) {

        }
    };


    private boolean checkLogin() {
        return AccessToken.getCurrentAccessToken() == null;
    }
}
