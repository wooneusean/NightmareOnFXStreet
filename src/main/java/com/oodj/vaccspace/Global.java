package com.oodj.vaccspace;

import com.oodj.vaccspace.controllers.dashboard.DashboardController;
import com.oodj.vaccspace.models.Citizen;
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

    public static Citizen getLoggedInUser() {
        return TextORM.getOne(Citizen.class, data -> Integer.parseInt(data.get("id")) == userId);
    }
}
