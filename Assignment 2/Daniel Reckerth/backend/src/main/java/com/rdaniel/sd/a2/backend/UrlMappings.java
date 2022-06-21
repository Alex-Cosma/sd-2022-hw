package com.rdaniel.sd.a2.backend;

public class UrlMappings {

  public static final String API_PATH = "/api";
  public static final String BOOKS_PATH = API_PATH + "/books";

  public static final String USERS_PATH = API_PATH + "/users";
  public static final String REGULAR_USERS_PATH = "/regular";
  public static final String RESOURCE_BY_ID = "/{id}";
  public static final String EXPORT_REPORT = "/export/{type}";

  public static final String FILTERED_PATH = "/filtered";

  public static final String AUTH_PATH = API_PATH + "/auth";
  public static final String SIGN_IN_PATH = "/sign-in";
  public static final String SIGN_UP_PATH = "/sign-up";

  public static final String TEST_PATH = API_PATH + "/test";
}
