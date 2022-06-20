package com.project.clinic;

public class URLMapping {
    public static final String API_PATH = "/api";
    public static final String BRAND = API_PATH + "/brand";
    public static final String INGREDIENT = API_PATH + "/ingredient";
    public static final String PRODUCT = API_PATH + "/product";
    public static final String TREATMENT = API_PATH + "/treatment";
    public static final String APPOINTMENT = API_PATH + "/appointment";
    public static final String WEATHER = API_PATH + "/weather";

    public static final String ID_PART = "/{id}";
    public static final String USERNAME = "/{username}";
    public static final String EXPORT_REPORT = "/export/{type}";
    public static final String SEARCH = "/search/{detail}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
    public static final String GET_ROLE = "/get-role";
    public static final String DERMATOLOGISTS = "/dermatologists";

    public static final String ADD = "/add";
    public static final String DELETE = "/delete";
    public static final String VIEW = "/view";
    public static final String UPDATE = "/update";
    public static final String FIND_ALL = "/findall";
    public static final String FIND = "/find";
    public static final String BUY = "/buy";

    public static final String USER = API_PATH + "/user";
    public static final String USERS_ID_PART = "/{id}";
    public static final String PRODUCT_ID_PART = "/{productId}";
    public static final String SKINCOLOR = "/skincolors";

    public static final String PRODUCT_BASE_URL = "https://skincare-api.herokuapp.com";
    public static final String GET_PRODUCTS = "/products";
    public static final String GET_INGREDIENT = "/ingredients";
}
