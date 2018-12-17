package com.example.zy_cp.cdsxtnewsapp.util;

public class Param {
    private static String sessionName = "";
    private static String sessionEmail = "";

    public static String getSessionName() {
        return sessionName;
    }
    public static String getSessionEmail() {
        return sessionEmail;
    }

    public static void setSessionName(String sessionName) {
        Param.sessionName = sessionName;
    }
    public static void setSessionEmail(String sessionEmail) {
        Param.sessionEmail = sessionEmail;
    }
}
