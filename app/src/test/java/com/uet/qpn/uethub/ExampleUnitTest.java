package com.uet.qpn.uethub;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Date date = new Date(1487833154000L);
        System.out.println(date.toString());
    }

    @Test
    public void getNewsData() {
       /* Context context = new MainActivity();
        RequestQueue requestQueue = Volley.newRequestQueue(context);*/
        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority("localhost")
                .appendPath("api")
                .appendPath("v1")
                .appendPath("news")
                .appendPath("getEntitiesByNews")
                .appendQueryParameter("news","FIT")
                .appendQueryParameter("page","0").build();
        System.out.println(uri.toString());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://localhost:8080/api/v1/news/getEntitiesByNews?news=fit&page=0", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

       // requestQueue.add(stringRequest);
    }
}