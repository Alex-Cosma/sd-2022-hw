package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String BOOKS = API_PATH + "/books";
    public static final String BOOKS_ID_PART = "/{id}";
    public static final String GENRES = "/genres";
    public static final String FILTER = "/filter";
    public static final String EXPORT_REPORT = "/export/{type}";
    public static final String REPORT = API_PATH + "/reports";
    public static final String PDF = "/pdf";
    public static final String CSV = "/csv";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";

    public static final String REPORT_LOCAL_PATH = "reports";
    public static final String REPORT_LOCAL_PDF = "/report.pdf";
    public static final String REPORT_LOCAL_CSV = "/report.csv";

}
