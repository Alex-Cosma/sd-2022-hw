package com;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String POSTS = API_PATH + "/posts/{userId}";
    public static final String ENTITY = "/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String REGISTER = "/register";
    public static final String USERS = API_PATH+"/users";

    public static final String REPORT = POSTS+"/report/{type}";

}