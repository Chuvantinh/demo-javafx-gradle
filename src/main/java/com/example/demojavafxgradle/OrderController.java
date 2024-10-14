package com.example.demojavafxgradle;

import javafx.fxml.FXML;

import java.io.IOException;

public class OrderController {
    @FXML
    private void swichToHelloForm() throws IOException {
        HelloApplication.setRoot("hello-view");
    }
}
