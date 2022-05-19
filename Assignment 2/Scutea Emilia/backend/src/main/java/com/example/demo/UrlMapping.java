package com.example.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String BOOKS = "/admin/books";
    public static final String BOOKS_CREATE = BOOKS + "/create";
    public static final String BOOKS_ID_EDIT = BOOKS + "/edit/{id}";
    public static final String EXPORT_REPORT = BOOKS + "/export/{type}";
    public static final String BOOKS_ID_DELETE = BOOKS + "/delete/{id}";

    public static final String BOOKSTORE = "/bookstore";
    public static final String BOOKSTORE_ID_SELL = BOOKSTORE + "/sell/{id}";
    public static final String BOOKSTORE_SEARCH_BOOKS = BOOKSTORE + "/search/{str}";
    public static final String BOOKSTORE_GENRE_TYPES = BOOKSTORE + "/genretypes";
    public static final String BOOKSTORE_BOOK_BY_GENRE = BOOKSTORE + "/getByGenre/{genre}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";
    public static final String USERS_ID_EDIT = "/edit/{id}";
    public static final String USERS_ID_DELETE = "/delete/{id}";


    public static final String CART = BOOKSTORE + "/cart";
    public static final String CREATE_CART = "/create/{id}";
    public static final String ADD_TO_CART = "/add/{id}";
    public static final String DELETE_FROM_CART = "/delete/{id}";
    public static final String DELETE_CART = "/deletecart/{id}";
    public static final String PLACE_ORDER = "/order/{id}/{user_id}";


    public static final String REVIEWS = API_PATH + "/reviews";
    public static final String REVIEW_GET = "/{id}";
    public static final String REVIEW_ADD_BOOK = "/addreviews/{book_id}/{user_id}";
    public static final String REVIEW_RATINGS = "/ratings";
}
