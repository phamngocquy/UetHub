package com.uet.qpn.uethub.fcm;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.squareup.picasso.Picasso;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.volleyGetDataNews.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by javis on 3/18/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        //   Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {
        // TODO: Implement this method to send token to your app server.


        Log.d(TAG, "token: " + token);
        //    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        // dDbHzm9GeMA:APA91bFWABpS8tDwoTVEw8OHA025W3tmP7cMY0XQ9_7Lfwyv1xtZjLGK3mhqJzKrEmn9BqsQe63bejZ_chzuAQAutItCVbO3wzEZC3jBg5pKvImc6qJ65DOLAv6b-h4Dzb18plxss4sb
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        Profile profile = Profile.getCurrentProfile();

        if (profile != null) {
            Log.d("fb___aa", "da dang nhap fb");
            final Uri uri = profile.getProfilePictureUri(150, 150);

            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        String email = object.getString("email");
                        updateDeviceToken(email, token);
                    } catch (JSONException e) {
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

    public void updateDeviceToken(final String email, final String fcmToken) {
        Log.d("updateToken", "cap nhat token");
        String url = Configuration.HOST + Configuration.API_PATH_UPDATE_FCM;
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("fcm", fcmToken);

                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);

    }

    public String getInstanceID() {
        return FirebaseInstanceId.getInstance().getToken();
    }
}
