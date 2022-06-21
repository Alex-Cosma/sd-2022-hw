package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ITEMS = API_PATH + "/items";
    public static final String ITEMS_ID_PART = "/{id}";
    public static final String DELETE = "/delete/{id}";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";  //end point catre metoda de autentificare, sign in, register
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String ID = "/{id}";//???????

    public static final String USERS =API_PATH + "/users";
    public static final String USERS_ID_PART = "/{id}";

}
