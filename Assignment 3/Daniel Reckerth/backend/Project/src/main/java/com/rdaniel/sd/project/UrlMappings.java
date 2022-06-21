package com.rdaniel.sd.project;

public class UrlMappings {
  public static final String API_PATH = "/api";
  public static final String CUSTOMER_PATH =  API_PATH + "/customers";
  public static final String RESOURCE_BY_ID = "/{id}";
  public static final String DEVICES_PATH =  CUSTOMER_PATH +  RESOURCE_BY_ID + "/devices";
  public static final String DEVICES_WITH_TICKETS = "/with-tickets";
  public static final String DEVICE_ID_PATH = "/{deviceId}";

  public static final String TICKETS_PATH = DEVICES_PATH + DEVICE_ID_PATH + "/tickets";

  public static final String TICKET_ID_PATH = "/{ticketId}";


  public static final String USERS_PATH = API_PATH + "/users";
  public static final String REGULAR_USERS_PATH = "/customers";


  public static final String AUTH_PATH = API_PATH + "/auth";
  public static final String SIGN_IN_PATH = "/sign-in";
  public static final String SIGN_UP_PATH = "/sign-up";
  public static final String TEST_PATH = API_PATH + "/test";
}
