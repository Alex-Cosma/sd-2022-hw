package com.example.youtubeish;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";
    public static final String GET_USERS = "/get-users";
    public static final String GET_USER = "/get-users/{id}";
    public static final String DELETE_USER = "delete-user/{id}";
    public static final String ADD_USER = "/add-user";
    public static final String UPDATE_USER = "/update-user/{id}";

    public static final String VIDEOS = "/videos";
    public static final String GET_VIDEOS = "/get-videos";
    public static final String UPLOAD_VIDEO = "/upload-video";
}
