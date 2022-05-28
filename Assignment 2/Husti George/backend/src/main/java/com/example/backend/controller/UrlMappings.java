package com.example.backend.controller;

public class UrlMappings {
    public static final String URL_PREFIX = "/bookStore";

    public static final String ADMINISTRATOR = URL_PREFIX + "/administrator";
    public static final String REGULAR_USER = URL_PREFIX + "/regularUser";
    public static final String AUTH = URL_PREFIX + "/auth";

    public static final String FILTER_BOOKS = "/filterBooks";
    public static final String SELL_BOOK = "/sellBook";

    public static final String GET_ALL_BOOKS = "/getAllBooks";
    public static final String CREATE_BOOK = "/createBook";
    public static final String GET_BOOK = "/getBook";
    public static final String UPDATE_BOOK = "/updateBook";
    public static final String DELETE_BOOK = "/deleteBook";

    public static final String GET_BOOKS_BY_TITLE_FROM_API = "/getBooksByTitleFromApi";
    public static final String GET_BOOKS_BY_AUTHOR_FROM_API = "/getBooksByAuthorFromApi";

    public static final String CREATE_USER = "/createUser";
    public static final String GET_USER = "/getUser";
    public static final String UPDATE_USER = "/updateUser";
    public static final String DELETE_USER = "/deleteUser";

    public static final String GENERATE_REPORT_OUT_OF_STOCK_BOOKS = "/generateReportOutOfStockBooks";

    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
}
