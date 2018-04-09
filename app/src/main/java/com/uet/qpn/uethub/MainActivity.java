package com.uet.qpn.uethub;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
        ImageView img_news = tabHost.getTabWidget().getChildTabViewAt(0).findViewById(R.id.news_ic);
        img_news.setImageResource(R.drawable.ic_24_hours);
        Log.d("onCreate ManiActivity", "run in");
        tabHost.setOnTabChangedListener(onTab_Change);



    }

    private TabHost.OnTabChangeListener onTab_Change = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            ImageView img_news = tabHost.getTabWidget().getChildTabViewAt(0).findViewById(R.id.news_ic);
            ImageView img_exam_noti = tabHost.getTabWidget().getChildTabViewAt(1).findViewById(R.id.exam_noti_ic);
            ImageView img_exam_result = tabHost.getTabWidget().getChildTabViewAt(2).findViewById(R.id.result_noti_ic);
            ImageView img_setting = tabHost.getTabWidget().getChildTabViewAt(3).findViewById(R.id.setting_ic);
            switch (tabId) {
                case "news":
                    img_news.setImageResource(R.drawable.ic_24_hours);

                    img_exam_noti.setImageResource(R.drawable.ic_exam_noti_off);
                    img_exam_result.setImageResource(R.drawable.ic_exam_result_off);
                    img_setting.setImageResource(R.drawable.ic_settings_off);
                    break;
                case "exam":
                    img_exam_noti.setImageResource(R.drawable.ic_exam_noti);

                    img_news.setImageResource(R.drawable.ic_24_hours_off);
                    img_exam_result.setImageResource(R.drawable.ic_exam_result_off);
                    img_setting.setImageResource(R.drawable.ic_settings_off);

                    break;
                case "result":
                    img_exam_result.setImageResource(R.drawable.ic_exam_result);

                    img_exam_noti.setImageResource(R.drawable.ic_exam_noti_off);
                    img_news.setImageResource(R.drawable.ic_24_hours_off);
                    img_setting.setImageResource(R.drawable.ic_settings_off);

                    break;
                case "setting":
                    img_setting.setImageResource(R.drawable.ic_settings);

                    img_exam_result.setImageResource(R.drawable.ic_exam_result_off);
                    img_exam_noti.setImageResource(R.drawable.ic_exam_noti_off);
                    img_news.setImageResource(R.drawable.ic_24_hours_off);
                    break;
            }


        }
    };

    /*Ngu lol function*/

}
