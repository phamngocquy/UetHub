package com.uet.qpn.uethub.config;


public class Configuration {
    public static String API_PATH_NEWS = "api/v1/news/getEntitiesByNews?";
    public static String API_PATH_EXAM_RESULT = "api/v1/grade/getAllSubjects";
    public static String API_PATH_EXAM = "api/v1/grade/getAllExam";
    public static String API_PATH_UPDATE_FCM = "/api/v1/user/updatefcmToken";
    public static String APT_PATH_UPDATE_NEW_REGISTER = "api/v1/user/updateNewsRegister";
    public static String API_PATH_UPDATE_MSV = "api/v1/user/updateMSV";
    public static String API_PATH_GET_NEW_SW = "api/v1/news/getNewsRegByUser";
    public static String API_PATH_GET_CREATE_USER = "api/v1/user/createUser";
    public static String API_PATH_REMOVE_FCM = "/api/v1/user/deletefcmToken";
    public static String API_PATH_GET_CONFIG = "/api/v1/user/getConfig";
    public static String API_PATH_FORM = "api/v1/form/getAll";

    public static String HOST = "http://192.168.1.104:8080/";
    public static String STORE_FOLDER = "UHDownload";
    public static String USER_KEY = "MYUSER";
}
