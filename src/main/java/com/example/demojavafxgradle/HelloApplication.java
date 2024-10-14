package com.example.demojavafxgradle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        scene = new Scene(loadFXML("hello-view"), 820, 840);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Add the CSS file

        stage.setTitle("Demo JavaFX mit Gradle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    static void setRoot( String fmxl) throws IOException {
        scene.setRoot(loadFXML(fmxl));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}