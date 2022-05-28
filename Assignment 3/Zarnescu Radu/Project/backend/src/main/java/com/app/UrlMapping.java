package com.app;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String MOVIES = API_PATH + "/movies";
    public static final String MOVIE_ID = "/{id}";
    public static final String CREATE_MOVIE = "/create";
    public static final String EDIT_MOVIE = "/edit";
    public static final String DELETE_MOVIE = "/delete/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";
    public static final String USER_CREATE = "/create";
    public static final String USER_EDIT = "/{id}";
    public static final String DELETE_USER = "/delete/{id}";

    public static final String REVIEWS = API_PATH + "/reviews";
    public static final String GET_REVIEWS = "/{id}";
    public static final String ADD_REVIEW = "/add";

    public static final String WATCHLIST = API_PATH + "/watchlist";
    public static final String ADD_WATCHLIST = "/add";
    public static final String IS_IN_WATCHLIST = "/{userId}/{movieId}";
    public static final String GET_WATCHLIST = "/{userId}";
    public static final String REMOVE_FROM_WATCHLIST = "/remove";
}
