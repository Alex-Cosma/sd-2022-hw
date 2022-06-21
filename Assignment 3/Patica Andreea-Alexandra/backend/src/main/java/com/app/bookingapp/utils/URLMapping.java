package com.app.bookingapp.utils;

public class URLMapping {
    public static final String API_PATH = "/api";

    //authentication
    public static final String AUTH = API_PATH + "/auth";  //end point catre metoda de autentificare, sign in, register
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    //users
    public static final String USERS = API_PATH + "/users";

    //property
    public static final String PROPERTY = API_PATH + "/property";

    //book
    public static final String BOOK = API_PATH + "/book";
    public static final String NEW = "/new/{username}";
    public static final String PDF = "/pdf";


    public static final String ID = "/{id}";
    public static final String USERNAME = "/{username}";
    public static final String DELETE = "/delete";
}
