package com.officemap.common;

public class ApplicationUrl {
    public static final String URL_V1 = "api/v1";
    public static final String URL_COUNTRY = URL_V1 + "/country";
    public static final String URL_CITY = URL_COUNTRY + "/city";
    public static final String URL_BUILDING = URL_CITY + "/building";
    public static final String URL_FLOOR = URL_BUILDING + "/floor";
    public static final String URL_ROOM = URL_FLOOR + "/room";
    public static final String URL_DESK = URL_ROOM + "/desk";
}
