module com.oodj.myvaksin {
    requires javafx.controls;
    requires javafx.fxml;

    requires MaterialFX;

    opens com.oodj.myvaksin to javafx.fxml;
    exports com.oodj.myvaksin;
}