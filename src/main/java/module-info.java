module com.oodj.vaccspace {
    requires javafx.controls;
    requires javafx.fxml;

    requires MaterialFX;
    requires io.github.euseanwoon;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    opens com.oodj.vaccspace to javafx.fxml;
    exports com.oodj.vaccspace;
    exports com.oodj.vaccspace.utils;
    opens com.oodj.vaccspace.utils to javafx.fxml;
    exports com.oodj.vaccspace.controllers;
    opens com.oodj.vaccspace.controllers to javafx.fxml;
    exports com.oodj.vaccspace.controllers.dashboard;
    opens com.oodj.vaccspace.controllers.dashboard to javafx.fxml;
    exports com.oodj.vaccspace.controllers.login;
    opens com.oodj.vaccspace.controllers.login to javafx.fxml;
    exports com.oodj.vaccspace.controllers.register;
    opens com.oodj.vaccspace.controllers.register to javafx.fxml;
    exports com.oodj.vaccspace.controllers.appointments;
    opens com.oodj.vaccspace.controllers.appointments to javafx.fxml;
}