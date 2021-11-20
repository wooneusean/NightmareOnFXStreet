package com.oodj.vaccspace;

import com.oodj.vaccspace.models.Person;
import textorm.TextORM;

public class Global {
    private static int userId;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Global.userId = userId;
    }

    public static Person getLoggedInUser() {
        return TextORM.getOne(Person.class, hashMap -> Integer.parseInt(hashMap.get("id")) == userId);
    }
}
