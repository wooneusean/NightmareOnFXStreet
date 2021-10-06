module com.oodj.vaccspace {
    requires javafx.controls;
    requires javafx.fxml;

    requires MaterialFX;
    requires io.github.euseanwoon;

    opens com.oodj.vaccspace to javafx.fxml;
    exports com.oodj.vaccspace;
    exports com.oodj.vaccspace.utils;
    opens com.oodj.vaccspace.utils to javafx.fxml;
    exports com.oodj.vaccspace.controllers;
    opens com.oodj.vaccspace.controllers to javafx.fxml;
}