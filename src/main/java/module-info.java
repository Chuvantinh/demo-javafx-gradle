module com.example.demojavafxgradle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.demojavafxgradle to javafx.fxml;
    exports com.example.demojavafxgradle;
}