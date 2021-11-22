package com.oodj.vaccspace;

import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.controllers.dashboard.DashboardController;
import textorm.TextORM;

public class Global {
    private static DashboardController dashboardReference;

    private static int userId;

    public static DashboardController getDashboardReference() {
        return dashboardReference;
    }

    public static void setDashboardReference(DashboardController dashboardReference) {
        Global.dashboardReference = dashboardReference;
    }

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
