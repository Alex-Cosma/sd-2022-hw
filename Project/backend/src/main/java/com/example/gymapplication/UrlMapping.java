package com.example.gymapplication;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String TRAININGS = API_PATH + "/trainings";
    public static final String TRAINING_ID_PATH = "/{id}";
    public static final String FILTER = "/filter/{filter}";
    //public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";
    public static final String USER_ID_PATH = "/{id}";
    public static final String USER_ID_TRAINING_ID_PATH = "/{userId}/{trainingId}";
}
