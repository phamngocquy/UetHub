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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.gc.materialdesign.views.ButtonFlat;
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
}
