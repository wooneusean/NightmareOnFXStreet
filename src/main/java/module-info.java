module win.woon.nightmareonfxstreet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens win.woon.nightmareonfxstreet to javafx.fxml;
    exports win.woon.nightmareonfxstreet;
}