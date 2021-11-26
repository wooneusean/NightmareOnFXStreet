package com.oodj.vaccspace;

import com.oodj.vaccspace.controllers.dashboard.DashboardController;
import com.oodj.vaccspace.controllers.home.HomeController;
import com.oodj.vaccspace.models.Person;
import textorm.TextORM;

public class Global {
    private static HomeController homeReference;

    private static DashboardController dashboardReference;

    private static int userId;

    private static boolean isCommittee = false;

    public static boolean isCommittee() {
        return isCommittee;
    }

    public static void setIsCommittee(boolean isCommittee) {
        Global.isCommittee = isCommittee;
    }

    public static HomeController getHomeReference() {
        return homeReference;
    }

    public static void setHomeReference(HomeController homeReference) {
        Global.homeReference = homeReference;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Global.userId = userId;
    }

    public static DashboardController getDashboardReference() {
        return dashboardReference;
    }

    public static void setDashboardReference(DashboardController dashboardReference) {
        Global.dashboardReference = dashboardReference;
    }

    public static Person getLoggedInUser() {
        return TextORM.getOne(Person.class, hashMap -> Integer.parseInt(hashMap.get("id")) == userId);
    }
}
