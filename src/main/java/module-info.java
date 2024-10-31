module com.example.demojavafxgradle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;

    opens com.orders.vantinh to javafx.fxml;
    exports com.orders.vantinh;
}