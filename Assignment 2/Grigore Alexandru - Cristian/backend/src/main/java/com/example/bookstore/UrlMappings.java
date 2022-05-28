package com.example.bookstore;

public class UrlMappings {

    public static final String API_URL = "/api";
    public static final String BOOKS = API_URL + "/books";
    public static final String FILTERED = "/{genre}";
    public static final String FILTEREDTITLE = "/filtered/{title}";
    public static final String FILTEREDAUTHOR = "/filtered/author/{author}";
    public static final String EXPORT_REPORT = "/report/{type}";
    public static final String ENTITY = "/{id}";

    public static final String AUTH = API_URL + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String USER = API_URL + "/user";

}
