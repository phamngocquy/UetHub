package com.uet.qpn.uethub;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import com.uet.qpn.uethub.config.Configuration;
import com.uet.qpn.uethub.entity.Form;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.entity.SubjectGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
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


                //Log.d("xxx", entity.toString());
                entities.add(entity);
            }
        } catch (JSONException e) {
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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm dd-MM-yyy");
                Date dateCreated = new Date(dataNumCreatedTime);
                Date dateUpdated = new Date(dataNumUpdatedTime);

                subject.setPublic_time(simpleDateFormat.format(dateCreated));
                subject.setUpdate_on(simpleDateFormat.format(dateUpdated));

                subjects.add(subject);
            }
        } catch (JSONException e) {
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


    public static List<SubjectGroup> getSubjectGroup(String response) {
        List<SubjectGroup> subjectGroups = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                SubjectGroup subjectGroup = new SubjectGroup();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                subjectGroup.setMsv(jsonObject.getString("msv"));
                subjectGroup.setSbdUser(jsonObject.getString("sbdUser"));
                subjectGroup.setExamDay(jsonObject.getString("examDay"));
                subjectGroup.setExamRoom(jsonObject.getString("examRoom"));
                subjectGroup.setTypeExam(jsonObject.getString("typeExam"));
                subjectGroup.setSubjectName(jsonObject.getString("subjectName"));
                subjectGroup.setSubjectCode(jsonObject.getString("subjectCode"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm dd-MM-yyy");
                Date dateUpdated = Calendar.getInstance().getTime();
                subjectGroup.setUpdate_on(simpleDateFormat.format(dateUpdated));

                subjectGroups.add(subjectGroup);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.w("length", String.valueOf(subjectGroups.size()));

        return subjectGroups;

    }

    public static List<String> getReg(String response) {
        List<String> stringList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                String reg = "";
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                reg = jsonObject.getString("name");

                stringList.add(reg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.w("length", String.valueOf(stringList.size()));

        return stringList;

    }
}
