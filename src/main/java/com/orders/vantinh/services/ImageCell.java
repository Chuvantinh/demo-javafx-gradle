package com.orders.vantinh.services;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

public class ImageCell<S> extends TableCell<S, String> {

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else{
            try {
                Image image = new Image(getClass().getResource("/images/products/" + item).toExternalForm());
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
