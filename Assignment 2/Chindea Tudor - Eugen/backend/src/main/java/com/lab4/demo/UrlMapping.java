package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ITEMS = API_PATH + "/items";
    public static final String ITEMS_ID_PART = "/{id}";
    public static final String ITEMS_SEARCH_PART = "/{searchedBook}";

    public static final String ITEMS_SELL_ID_PART = "/sell/{id}";

    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/users";
    public static final String USERS_ID_PART = "/{id}";


}
