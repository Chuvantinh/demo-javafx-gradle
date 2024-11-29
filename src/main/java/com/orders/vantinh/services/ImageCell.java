package com.orders.vantinh.services;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

import java.io.File;
import java.net.URL;

public class ImageCell<S> extends TableCell<S, String> {

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else{
            try {
                String imagePath= System.getProperty("user.dir") + "/images/products/" + item;
                String defautPath = System.getProperty("user.dir") + "/images/products/default-featured-image.jpg";
                Image image;
                if(new File(imagePath).exists()){
                     image = new Image(new File(imagePath).toURI().toString());
                }else{
                     image = new Image(new File(defautPath).toURI().toString());
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                // setGraphic(imageView);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(imageView);

                Popup popup = new Popup();
                // Set up the popup content (larger image)
                ImageView largeImageView = new ImageView(image);
                largeImageView.setFitHeight(300); // Set large height for popup
                largeImageView.setFitWidth(300);  // Set large width for popup

                imageView.setOnMouseClicked(event -> {
                  popup.getContent().clear();
                  popup.getContent().add(largeImageView);
                  popup.show(imageView, event.getScreenX(), event.getScreenY());
                });

                largeImageView.setOnMouseClicked(event -> popup.hide());

                setGraphic(stackPane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
