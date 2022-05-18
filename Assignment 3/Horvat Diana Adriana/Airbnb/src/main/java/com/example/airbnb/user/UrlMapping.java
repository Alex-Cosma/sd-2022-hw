package com.example.airbnb.user;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ENTITY = "/{id}";
//    public static final String FILTERED = "/filtered";
//    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";
    public static final String USER_ACCOMMODATIONS = ENTITY + "/accommodations";
    public static final String ACCOMMODATIONS = API_PATH + "/accommodations";
    public static final String BOOKINGS = API_PATH + "/bookings";
}
