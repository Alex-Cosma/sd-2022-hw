package com.raulp;

public class UrlMapping {
    public static final String CHECKWX_URL = "https://api.checkwx.com";
    public static final String CHECKWX_API_KEY = "8314eef2d3e94600aca0b30e7f";
    public static final String API_PATH = "/api";
    public static final String WEATHER_API = API_PATH + "/weather";
    public static final String METAR = "/metar";
    public static final String TAF = "/taf";
    public static final String BOOKS = API_PATH + "/books";
    public static final String BOOKS_ID_PART = "/{id}";
    public static final String GENRES = "/genres";
    public static final String FILTER = "/filter";
    public static final String EXPORT_REPORT = "/reports/{type}/user/{userId}";

    public static final String PDF = "/pdf";
    public static final String CSV = "/csv";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = "/user";

    public static final String REPORT_LOCAL_PATH = "reports";
    public static final String REPORT_LOCAL_PDF = "/report.pdf";
    public static final String REPORT_LOCAL_CSV = "/report.csv";

    public static final String STUDENTS = "/students";
    public static final String INSTRUCTORS = "/instructors";

    public static final String MY_STUDENTS = "/my-students/{id}";
    public static final String UNASSIGNED_STUDENTS = "/unassigned-students";
    public static final String USER_DETAILS = "/user/{id}";
    public static final String ADD_STUDENT = "/add-student/{studentId}/instructor/{instructorId}";
    public static final String FLIGHTS = "/flights";
    public static final String ADD_FLIGHT = "/add-flight";
    public static final String GET_FLIGHT = "/{instructorId}/withStudent/{studentId}";
    public static final String AIRPLANES = "/airplanes";
    public static final String AIRPORTS = "/airports";

    public static final String UNASSIGN_STUDENT = "/unassign-student";
}
