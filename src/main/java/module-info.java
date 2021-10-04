module com.oodj.vaccspace {
    requires javafx.controls;
    requires javafx.fxml;

    requires MaterialFX;

    opens com.oodj.vaccspace to javafx.fxml;
    exports com.oodj.vaccspace;
}