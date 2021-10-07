package com.oodj.vaccspace;

public class Global {
    private static int userId;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Global.userId = userId;
    }
}
