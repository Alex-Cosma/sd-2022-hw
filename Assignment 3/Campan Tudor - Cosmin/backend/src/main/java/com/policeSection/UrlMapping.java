package com.policeSection;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String FINES = API_PATH + "/fines";
    public static final String FINES_ID_PART = "/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";

    public static final String USER_ID_PART = "/{id}";

    public static final String DRIVERS = API_PATH + "/drivers";
    public static final String CREATE_DRIVER = "/create";
    public static final String CREATE_FINE = "/create";
    public static final String DRIVERS_ID_PART = "/{id}";

    public static final String GIVE_FINE ="/giveFine/{id}";

    public static final String PAY_FINE ="/payFine/{id}";

    public static final String DWN_PDF =API_PATH+"/report";


}
