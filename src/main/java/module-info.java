module com.orders.vantinh {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires java.net.http;
    requires org.apache.poi.ooxml;
    requires org.mongodb.driver.core;
    requires commons.math3;

    opens com.orders.vantinh to javafx.fxml;
    exports com.orders.vantinh;
    exports com.orders.vantinh.models;
    opens com.orders.vantinh.models to javafx.fxml;
    exports com.orders.vantinh.dao;
    opens com.orders.vantinh.dao to javafx.fxml;
    exports com.orders.vantinh.controllers;
    opens com.orders.vantinh.controllers to javafx.fxml;
}