package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ENTITY = "/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";

    public static final String STOCKS  = API_PATH + "/stocks";
    public static final String STOCKS_ID = "/{id}";
    public static final String BUY_QUANTITY = "/buy/{id}/{quantity}";
    public static final String SELL_QUANTITY = "/sell/{id}/{quantity}";
    public static final String MAIL = "/mail";

    public static final String INVESTMENTS = API_PATH + "/investments";
    public static final String INVESTMENTS_ID = "/{id}";
}
