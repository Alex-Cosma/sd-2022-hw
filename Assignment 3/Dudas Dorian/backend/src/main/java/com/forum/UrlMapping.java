package com.forum;

public class UrlMapping {
    public static final String API_PATH = "/api";

    public static final String CATEGORY = API_PATH + "/category";
    public static final String CATEGORY_ID_PART = "/{id}";

    public static final String THREAD = API_PATH + "/thread";
    public static final String THREAD_ID_PART = "/{id}";
    public static final String OF_CATEGORY_ID_PART = "/of_category/{id}";

    public static final String POST = API_PATH + "/post";
    public static final String POST_ID_PART = "/{id}";
    public static final String OF_THREAD_ID_PART = "/of_thread/{id}";

    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";
    public static final String USER_ID_PART = "/{id}";
    public static final String REGULAR = "/regular_users";
    public static final String NON_ADMIN = "/non_admins";

}
