package com.assignment2.bookstoreapp;

public class URLMapping {
    public static final String API_PATH = "/api";

    public static final String BOOKS = API_PATH + "/books";
    public static final String BOOKS_ID_PART = "/{id}";
    public static final String EXPORT_REPORT = "/export/{type}";
    public static final String BOOKS_OUT_OF_STOCK_REPORT = "/books-out-of-stock";
    public static final String SEARCH = "/search/{detail}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String GET_ROLE = "/get-role";

    public static final String ADD = "/add";
    public static final String DELETE = "/delete";
    public static final String VIEW = "/view";
    public static final String UPDATE = "/update";
    public static final String FIND_ALL = "/findall";
    public static final String FIND = "/find";
    public static final String SELL = "/sell";

    public static final String USER = API_PATH + "/user";
    public static final String USERS_ID_PART = "/{id}";
}
