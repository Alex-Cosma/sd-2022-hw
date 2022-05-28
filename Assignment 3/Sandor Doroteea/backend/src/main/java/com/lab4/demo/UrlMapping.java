package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String TRACKS = API_PATH+"/tracks";
    public static final String API_TRACKS = API_PATH+"/tracksAPI";
    public static final String TRACK_ID_PART = "/{id}";
    public static final String BUY_TRACK = "/purchase/{id}";

    public static final String PLAYLIST = API_PATH + "/playlists";
    public static final String PLAYLIST_ID_PART = "/{id}" ;
    public static final String ADD_PLAYLIST = "/create" + PLAYLIST_ID_PART ;

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";
    public static final String USER_ID_PART = "/{id}";

    public static final String COMMENTS = API_PATH + "/comments";
    public static final String COMMENT_ID_PART = "/{id}";

    public static final String PAYMENT = API_PATH + "/payment";
    public static final String FAIL_PAYMENT = "http://localhost:8091"+"/payment/fail";
    public static final String SUCCESS_PAYMENT = "http://localhost:8091"+"payment/success";

    public static final String EXPORT_REPORT = "/{type}/{id}";

}