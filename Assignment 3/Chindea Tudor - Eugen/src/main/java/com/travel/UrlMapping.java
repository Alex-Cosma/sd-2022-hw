package com.travel;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ITEMS = API_PATH + "/items";
    public static final String ITEMS_ID_PART = "/{id}";
    public static final String HOTEL = API_PATH + "/hotels";
    public static final String BOOKINGH = API_PATH + "/bookinghotels";
    //public static final String BOOKINGH2 = API_PATH + "/bookinghotelsedit";
    public static final String BOOKINGH_ID_PART = "/{id}";
    public static final String BOOKINGF = API_PATH + "/bookingflights";
    //public static final String BOOKINGF2 = API_PATH + "/bookingflightsedit";
    public static final String BOOKINGF_ID_PART = "/{id}";
    public static final String FLIGHT = API_PATH + "/flights";
    public static final String FLIGHT_ID_PART = "/{id}";
    public static final String HOTEL_ID_PART = "/{id}";

    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/users";
    public static final String USERS_ID_PART = "/{id}";


}
