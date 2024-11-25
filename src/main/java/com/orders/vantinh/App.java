package com.orders.vantinh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));

        scene = new Scene(loadFXML("login-view"), 1200, 600);
        scene.getStylesheets().add(getClass().getResource("/mobile.css").toExternalForm()); // Add the CSS file

        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            setReponsiveStyles(stage.getScene(), newValue.doubleValue());
        });

        stage.setTitle("TD Orders");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setRoot( String fmxl) throws IOException {
        scene.setRoot(loadFXML(fmxl));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void setReponsiveStyles(Scene scene, double width) {
        if(width < 600){
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/mobile.css").toExternalForm()); // Mobile
        }else if (width >= 601 && width < 1200){
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/tablet.css").toExternalForm()); // Tablet
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/desktop.css").toExternalForm()); // desktop

        }
    }
}