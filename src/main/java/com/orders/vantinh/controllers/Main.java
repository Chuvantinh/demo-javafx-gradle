package com.orders.vantinh.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import com.orders.vantinh.App;

public class Main {
    @FXML
    private Button btnProducts;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnOrders;

    @FXML
    public void switchToOrders() {
        loadView("orders-view");
    }

    public void switchToProducts() {
        loadView("products-view");
    }

    public void switchToCustomers() {
        loadView("customer-view");
    }

    public void switchToSettings() {
        loadView("settings-view");
    }

    private void loadView(String fxmlFile){
        try{
            App.setRoot(fxmlFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
