package com.example.alrizq.utils;

import java.util.HashMap;

public class Constant {
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";
    public static final String REMOTE_MSG_CONTENT_TYPE = "type";
    public static String value = "";
    public static String phoneNumber = "";
    public static String userRole = "";
    public static String selectedRole = "";
    public static String userId = "";
    public static String userName = "";
    public static final String API_KEY_SERVER = "AAAAVvJIbsE:APA91bFchj0rOsJUv5XKyPa7Sq94xAPKSNvcGpUPRnRz3iU6UQYAeWvBb7i3aNKzOX0Lc20LtQEzHnbmaNUJnfUBhl6pbj5xdAbSyDV-HvqndVHjMV81ZOsN03Zn-BsAZ_ZVxUZXGdmV";
    public static String FcmToken = "";
    public static String userEmail = "";
    public static String userAbout = "";
    public static int activity=0;
    public static String donationID="";

    public static HashMap<String, String> getRemoteMessageHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(
                "Authorization", "key=" + API_KEY_SERVER
        );
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
