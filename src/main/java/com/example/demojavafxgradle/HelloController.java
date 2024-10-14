package com.example.demojavafxgradle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.IOException;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private RadioButton radioWelcome;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> items;

    @FXML
    private ImageView imageViewWelcome;

    @FXML
    public void initialize() {
        items = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3", "Item 4");

        // Add the items to the listView
        listView.setItems(items);

        // Handle item selection
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item:" + newValue);
        });

        Image image = new Image(getClass().getResourceAsStream("/images/Webinar_Maghaireh_home_1080.jpg"));
        if( image == null){
            System.out.println("Image is null");
        }{
            imageViewWelcome.setImage(image);
        }
    }

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!");
        welcomeText.setStyle("-fx-text-fill: red;");
    }

    // Action for "New" menu item
    @FXML
    protected void handleNewAction() {
        showAlert("New File", "New file created!");
    }

    // Action for "Open" menu item
    @FXML
    protected void handleOpenAction() {
        showAlert("Open File", "Open file dialog!");
    }

    // Action for "Exit" menu item
    @FXML
    protected void handleExitAction() {
        System.exit(0); // Exits the application
    }

    // Action for "Cut" menu item
    @FXML
    protected void handleCutAction() {
        showAlert("Cut", "Cut action performed!");
    }

    // Action for "Copy" menu item
    @FXML
    protected void handleCopyAction() {
        showAlert("Copy", "Copy action performed!");
    }

    // Action for "Paste" menu item
    @FXML
    protected void handlePasteAction() {
        showAlert("Paste", "Paste action performed!");
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void handRadioWelcome() {
        radioWelcome.setSelected(true);
        showAlert("Radio Welcome", "Welcome to click option");
    }

    @FXML
    private void switchToOrderView() throws IOException {
        HelloApplication.setRoot("order-view");
    }
}