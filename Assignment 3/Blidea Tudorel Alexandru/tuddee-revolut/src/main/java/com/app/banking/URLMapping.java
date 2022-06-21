package com.app.banking;

public class URLMapping {
    public static final String API_PATH = "/api";
    public static final String ID = "/{id}";

    //cards
    public static final String CARDS = API_PATH + "/cards";
    public static final String MY_CARDS = "/my_cards";

    //deposits
    public static final String DEPOSITS = API_PATH + "/deposits";
    public static final String MY_DEPOSITS = "/my_deposits/{id}";

    //users
    public static final String USERS = API_PATH + "/users";
    public static final String USERS_STATISTICS = USERS + "/statistics";

    //reports
    public static final String REPORTS = API_PATH + "/reports";

    //emails
    public static final String EMAIL = API_PATH + "/email";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/signin";
    public static final String SIGN_UP = "/signup";

    // pay
    public static final String PAY = API_PATH + "/pay";

    public static final String OFFERS = API_PATH + "/offers";

    public static final String NEWSLETTER = API_PATH + "/newsletter";

    public static final String CONTACTS = API_PATH + "/contacts";

}
