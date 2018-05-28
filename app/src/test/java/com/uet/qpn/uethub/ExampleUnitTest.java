package com.uet.qpn.uethub;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        int r = 10 / 100;
        System.out.println(r);
    }

    @Test
    public void getNewsDataUET() {
        try {
            Document document = Jsoup.connect("https://uet.vnu.edu.vn/11476/").timeout(10000).get();

            Elements element_ = document.select(".single-content-title");
            System.out.println(element_);
            Elements element = document.select(".single-post-content-text").addClass("content-pad");
            System.out.println(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNewsDataFIT() {
        try {
            Document document = Jsoup.connect("http://fit.uet.vnu.edu.vn/20161124/").timeout(10000).get();

            Elements element = document.select(".entry-header");
            System.out.println(element);

            Elements element_ = document.select(".entry-content").addClass("clearfix");
            System.out.println(element_);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNewsDataFet() {

        try {
            Document document = Jsoup.connect("http://fet.uet.vnu.edu.vn/tuyen-dung-ky-su-cu-nhan-lam-viec-tai-samsung-r-d-ha-noi.html").timeout(10000).get();

            Elements element = document.select(".post-content").addClass("clearfix");
            System.out.println(element.select("h2"));

            Elements element_ = document.select(".entry").addClass("clearfix");
            System.out.println(element_);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNewsDataFepn() {
        try {
            Document document = Jsoup.connect("http://fepn.uet.vnu.edu.vn/sinh-vien/thong-bao/2018/04/hoc-bong-thac-si-tien-si/").timeout(10000).get();
            Elements element_ = document.select(".title").addClass("adelle");
            System.out.println(element_);
            Elements element = document.select(".post-content");
            System.out.println(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        Date date = Calendar.getInstance().getTime();
        Date date_ = df.parse("00:00:00 04-06-2018");
        Long diff = date_.getTime() - 2 * 24 * 60 * 60 * 1000;
        Date date1 = new Date(diff);
        System.out.println(df.format(date_));
        System.out.println(df.format(date1));


    }
}