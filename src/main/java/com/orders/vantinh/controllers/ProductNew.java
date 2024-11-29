package com.orders.vantinh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ProductNew {
    @FXML
    TextField productName;

    @FXML
    TextField productNameVN;

    @FXML
    TextArea productShortDescription;

    @FXML
    TextArea productDescription;

    @FXML
    ComboBox<String> categoryComboBox;

    @FXML
    private Button uploadImageButton;

    @FXML
    TextField productStock;


    // for pieces
    @FXML
    TextField PquantityInBaseUnit;

    @FXML
    TextField PunitRegularPrice;

    @FXML
    TextField PunitSalePrice;

    @FXML
    TextField PunitBuyPrice;

    // for box
    @FXML
    TextField BquantityInBaseUnit;

    @FXML
    TextField BunitRegularPrice;

    @FXML
    TextField BunitSalePrice;

    @FXML
    TextField BunitBuyPrice;

    // for carton
    @FXML
    TextField CquantityInBaseUnit;

    @FXML
    TextField CunitRegularPrice;

    @FXML
    TextField CunitSalePrice;

    @FXML
    TextField CunitBuyPrice;

    @FXML
    ImageView imageView;

    public ProductNew(){

    }

    @FXML
    public void initialize(){
        initCategoryCombobox();
    }

    private void initCategoryCombobox(){

    }

    public  boolean handleImageUpload(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("images Files", "*.png", "*.jpg", "*.gif", "*.webp")
        );

        File selectedFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }

        return true;
    }

    public void handleSubmit(){

    }

}
