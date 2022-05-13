package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ITEMS = API_PATH + "/items";
    public static final String ITEMS_ID_PART = "/{id}";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String EXPORT_CSV="/reportCSV";
    public static final String EXPORT_PDF="/reportPDF";
    public static final String USER =API_PATH+"/user";
    public static final String USER_ID_PART = "/{id}";
    public static final String CREATE_ITEM = "/create";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";


}
