module com.example.scolorpicker {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.kwhat.jnativehook;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires com.jfoenix;

    opens com.example.scolorpicker to javafx.fxml;
    exports com.example.scolorpicker;
}