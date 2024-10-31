package com.orders.vantinh;

import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.io.IOException;


public class LoginController {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void actionLogin() throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Get the "users" collection
        MongoCollection<Document> mongoCollection = DBConnection.getCollection("javafx-order-management", "users");

        Document query = new Document("username", username).append("password", password);
        Document user = mongoCollection.find(query).first();
        if( user != null ) {
            System.out.println("Login successful");
            App.setRoot("main-view");
        }else{
            System.out.println("Invalid username or password");
            showAlert("Login Failed", "Invalid username or password");
        }

    }
}