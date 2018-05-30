package com.uet.qpn.uethub;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.uet.qpn.uethub.MyAlarm.AlarmNotificationReceiver;
import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.Form;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.entity.SubjectGroup;
import com.uet.qpn.uethub.saveRealm.SaveUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Helper {

    public static ArrayList<Form> getForm(String response) {
        ArrayList<Form> listForms = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Form form = new Form();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                form.setName(jsonObject.getString("name"));
                form.setUrl(jsonObject.getString("url"));
                form.setLocal_url(jsonObject.getString("localUrl"));

                Long createdTime = Long.valueOf(jsonObject.getString("createdTime"));
                Long updatedTime = Long.valueOf(jsonObject.getString("updatedTime"));

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm dd-MM-yyy");

                Date date = new Date(createdTime);
                form.setCreateTime(simpleDateFormat.format(date));

                Date date_ = new Date(updatedTime);
                form.setUpdatedTime(simpleDateFormat.format(date_));

                listForms.add(form);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("NullPointerEx..");
            e.printStackTrace();
        }

        return listForms;
    }

    public static ArrayList<NewsEntity> getNewsEntity(String response, String newsName) {
        ArrayList<NewsEntity> entities = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                NewsEntity entity = new NewsEntity();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                entity.setAuthor(jsonObject.getString("author"));
                entity.setCategories(jsonObject.getString("categories"));
                entity.setDescription(jsonObject.getString("description"));
                entity.setTitle(jsonObject.getString("title"));
                entity.setUrl(jsonObject.getString("url"));
                entity.setNewsName(newsName);

                Long dataNum = Long.valueOf(jsonObject.getString("date"));
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm dd-MM-yyy");


                Date date = new Date(dataNum);
                entity.setPublictime(simpleDateFormat.format(date));

                entities.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("NullPointerEx..");
            e.printStackTrace();
        }


        return entities;
    }

    public static Boolean checkFileExist(String fileName) {
        try {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator;
            File file = new File(path + Configuration.STORE_FOLDER);
            for (File file_ : file.listFiles()) {
                if (file_.getName().equalsIgnoreCase(fileName)) return true;
            }
        } catch (NullPointerException e) {

            e.printStackTrace();
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator;

            File file = new File(path + Configuration.STORE_FOLDER);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return false;
    }

    public static List<Subject> getSubjectEntity(String response) {
        List<Subject> subjects = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Subject subject = new Subject();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                subject.setCode(jsonObject.getString("code"));
                subject.setName(jsonObject.getString("name"));
                subject.setUrl(jsonObject.getString("url"));
                subject.setLocal_url(jsonObject.getString("localUrl"));

                Long dataNumCreatedTime = Long.valueOf(jsonObject.getString("createdTime"));
                Long dataNumUpdatedTime = Long.valueOf(jsonObject.getString("updatedTime"));

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm dd-MM-yyy");
                Date dateCreated = new Date(dataNumCreatedTime);
                Date dateUpdated = new Date(dataNumUpdatedTime);

                subject.setPublic_time(simpleDateFormat.format(dateCreated));
                subject.setUpdate_on(simpleDateFormat.format(dateUpdated));

                subjects.add(subject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("NullPointerEx..");
            e.printStackTrace();
        }

        return subjects;

    }

    public static List<Subject> getSubjectEntity_Result(String response) {
        List<Subject> subjects = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Subject subject = new Subject();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                subject.setCode(jsonObject.getString("code"));
                subject.setName(jsonObject.getString("name"));
                subject.setUrl(jsonObject.getString("url"));
                subject.setLocal_url(jsonObject.getString("localUrl"));

                Long dataNumCreatedTime = Long.valueOf(jsonObject.getString("createdTime"));
                Long dataNumUpdatedTime = Long.valueOf(jsonObject.getString("updatedTime"));

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm dd-MM-yyy");
                Date dateCreated = new Date(dataNumCreatedTime);
                Date dateUpdated = new Date(dataNumUpdatedTime);

                subject.setPublic_time(simpleDateFormat.format(dateCreated));
                subject.setUpdate_on(simpleDateFormat.format(dateUpdated));

                if (!subject.getUrl().equalsIgnoreCase("null") &&
                        !subject.getLocal_url().equalsIgnoreCase("null")) {
                    subjects.add(subject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("NullPointerEx..");
            e.printStackTrace();
        }

        return subjects;

    }

    public static String getNewsPage(String content) {
        return "<!DOCTYPE html><html><head></head><body>" + content + "</body></html>";

    }

    public static String getContentRaw(Document document, String newsName) {
        StringBuilder result = new StringBuilder();
        Elements elements = new Elements();
        Elements elements_ = new Elements();
        switch (newsName) {
            case "UET":
                elements = document.select(".single-content-title");
                elements_ = document.select(".single-post-content-text").addClass("content-pad");
                break;
            case "FIT":
                elements = document.select(".entry-header");
                elements_ = document.select(".entry-content").addClass("clearfix");
                break;
            case "FET":
                elements = document.select(".post-content").addClass("clearfix").select("h2");
                elements_ = document.select(".entry").addClass("clearfix");

                break;
            case "FEPN":
                elements = document.select(".title").addClass("adelle");
                elements_ = document.select(".post-content");
                break;
        }

        result.append(elements.toString());
        result.append(elements_.toString());

        return result.toString();
    }

    public static String getNameByIdNews(String newsName) {
        switch (newsName) {
            case "UET":
                return "Trường đại học Công Nghệ";
            case "FIT":
                return "Khoa Công Nghệ Thông Tin";
            case "FET":
                return "Khoa Điện tử - Viễn Thông";
            case "FEPN":
                return "Khoa Vật Lỹ Kỹ Thuật và Công Nghệ NANO";
        }
        return "Unknown";
    }

    public static int getPageNumber(int sizeData) {
        int result = 0;
        int currentPage = sizeData / 10;
        if (currentPage == 0) return 0;

        if (sizeData % 10 >= 0) {
            result = currentPage;
        }
        return result;
    }


    public static ArrayList<SubjectGroup> getSubjectGroup(String response) {
        Log.d("json_", response);
        ArrayList<SubjectGroup> subjectGroups = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                SaveUser saveUser = new SaveUser();
                SubjectGroup subjectGroup = new SubjectGroup();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                subjectGroup.setMsv(saveUser.getMSV());
                subjectGroup.setSbdUser(jsonObject.getString("sbdUser"));

                subjectGroup.setExamRoom(jsonObject.getString("examRoom"));
                subjectGroup.setTypeExam(jsonObject.getString("typeExam"));
                subjectGroup.setSubjectName(jsonObject.getString("subjectName"));
                subjectGroup.setSubjectCode(jsonObject.getString("subjectCode"));

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm dd-MM-yyy");
                String tmp = jsonObject.getString("examDay");
                if (!tmp.equals("null")) {
                    Long examDay = Long.valueOf(tmp);
                    subjectGroup.setRawExamDay(examDay);
                    Log.d("date_", simpleDateFormat.format(new Date(examDay)));
                    subjectGroup.setExamDay(simpleDateFormat.format(new Date(examDay)));
                }
                subjectGroup.setUpdate_on(simpleDateFormat.format(new Date().getTime()));
                subjectGroups.add(subjectGroup);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return subjectGroups;

    }

    public static List<String> getReg(String response) {
        List<String> stringList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                String reg;
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                reg = jsonObject.getString("name");

                stringList.add(reg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public static boolean isOnlineABoolean(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static Long subtractionDate(Long examDate) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        Date currentDate = Calendar.getInstance().getTime();
        Long diff = new Date(examDate).getTime() - currentDate.getTime();
        diff = diff / 1000 / 60 / 60;
//        Date tmp = new Date(examDate);
//        Log.d("diffDate", String.valueOf(diff / 1000 / 60 / 60) + "/" + df.format(tmp));
        return diff;
    }

    public static void startAlarm(Context context, int timeNumber, SubjectGroup subjectGroup) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;
        intent = new Intent(context, AlarmNotificationReceiver.class);
        intent.putExtra("idNotification", Integer.valueOf(subjectGroup.getId()));
        intent.putExtra("subjectName", subjectGroup.getSubjectName());
        intent.putExtra("subjectCode", subjectGroup.getSubjectCode());
        pendingIntent = PendingIntent.getBroadcast(context, Integer.valueOf(subjectGroup.getId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, timeNumber);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        } else {
            assert alarmManager != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

    }
}
