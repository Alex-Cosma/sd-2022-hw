package com.example.backend;

public class UrlMapping {
    public static final String API_PATH = "/api";
/*    public static final String ITEMS = API_PATH + "/items";
    public static final String ITEMS_ID_PART = "/{id}";
    public static final String EXPORT_REPORT = "/export/{type}";*/
    public static final String GAMES = API_PATH + "/games";
    public static final String GAMES_ID_PART = "/{id}";
    public static final String REVIEWS = GAMES_ID_PART + "/review";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";
    public static final String USER_ID_PART = "/{id}";

    public static final String WISHLIST =API_PATH + "/wishlist/{idUser}";
    public static final String WISHLIST_GAME = "/{idGame}";
}
