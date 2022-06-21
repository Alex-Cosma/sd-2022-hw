package com.example.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String MOVIES = API_PATH + "/movies";
    public static final String MOVIE = "/id={id}";
    public static final String REVIEWS = "/id={id}/reviews";
    public static final String FILTERED = "/filtered/{filter}";
    public static final String MOVIES_ID_PART = "/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";
    public static final String REPORT=USERS+"/report";
    public static final String USERS_ID_PART = "/{id}";

    public static final String WATCHLIST=API_PATH+"/watchlist";
    public static final String GET_WATCHLIST="/{userId}";
    public static final String ADD_TO_WATCHLIST="/add/{userId}/{movieId}";
    public static final String REMOVE_FROM_WATCHLIST="/remove/{userId}/{movieId}";



}