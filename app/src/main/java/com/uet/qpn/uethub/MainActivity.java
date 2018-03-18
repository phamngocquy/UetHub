package com.uet.qpn.uethub;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.uet.qpn.uethub.fragment.fragment_viewpager.fragment_news;
import com.uet.qpn.uethub.fragment.fragment_viewpager.fragment_noti_exam;
import com.uet.qpn.uethub.fragment.fragment_viewpager.fragment_noti_result;
import com.uet.qpn.uethub.fragment.fragment_viewpager.fragment_setting;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //tabHost.setOnTabChangedListener(onTab_Change);

        LayoutInflater layoutInflater = getLayoutInflater();
        View news = layoutInflater.inflate(R.layout.news_indicator, null);
        View noti_exam = layoutInflater.inflate(R.layout.noti_exam_indicator, null);
        View noti_result = layoutInflater.inflate(R.layout.noti_result_indicator, null);
        View setting = layoutInflater.inflate(R.layout.setting_indicator, null);

        tabHost.addTab(tabHost.newTabSpec("news").setIndicator(news), fragment_news.class, null);
        tabHost.addTab(tabHost.newTabSpec("exam").setIndicator(noti_exam), fragment_noti_exam.class, null);
        tabHost.addTab(tabHost.newTabSpec("result").setIndicator(noti_result), fragment_noti_result.class, null);
        tabHost.addTab(tabHost.newTabSpec("setting").setIndicator(setting), fragment_setting.class, null);

        Log.d("onCreate ManiActivity", "run in");


    }

    /*private TabHost.OnTabChangeListener onTab_Change = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            switch (tabId) {
                case "new":
                    getSupportActionBar().setTitle("news");
                    break;
                case "exam":
                    getSupportActionBar().setTitle("exam");
                    break;
                case "result":
                    getSupportActionBar().setTitle("result");
                    break;
            }

        }
    };*/
}
