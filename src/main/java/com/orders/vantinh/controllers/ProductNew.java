package com.orders.vantinh.controllers;

import com.orders.vantinh.models.ModelProducts;
import com.orders.vantinh.models.ModelUnit;
import com.orders.vantinh.services.ProductService;
import com.orders.vantinh.utils.Notification;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static com.orders.vantinh.utils.Notification.showAlert;

public class ProductNew  {

    @FXML TextField SKU;
    @FXML TextField WP_ID;
    @FXML TextField productName;
    @FXML TextField productNameVN;
    @FXML TextArea productShortDescription;
    @FXML TextArea productDescription;
    @FXML ComboBox<String> categoryComboBox;
    @FXML private Button uploadImageButton;
    @FXML TextField productStock;
    @FXML TextField productTax;
    @FXML TextField productBrand;
    // for pieces
    @FXML TextField PquantityInBaseUnit;
    @FXML TextField PunitRegularPrice;
    @FXML TextField PunitSalePrice;
    @FXML TextField PunitBuyPrice;

    // for box
    @FXML TextField BquantityInBaseUnit;
    @FXML TextField BunitRegularPrice;

    @FXML TextField BunitSalePrice;
    @FXML TextField BunitBuyPrice;

    // for carton
    @FXML TextField CquantityInBaseUnit;
    @FXML TextField CunitRegularPrice;
    @FXML TextField CunitSalePrice;
    @FXML TextField CunitBuyPrice;

    @FXML ImageView imageView;
    @FXML Button submitButton;

    private String selectedImageName;
    private File selectedFile;
    ProductService productService = new ProductService();

    boolean isEditMode = false;
    private ModelProducts selectedProduct;

    public ProductNew() {

    }

    public void setProduct(ModelProducts product){
        this.isEditMode = true;
        this.selectedProduct = product;

        SKU.setText(product.getSKU());
        productName.setText(product.getProductName());
        productNameVN.setText(product.getProductNameVN());
        submitButton.setText("Update Data");
    }

    @FXML
    public void initialize() {
        initCategoryCombobox();
    }

    private void initCategoryCombobox() {

    }

    public boolean handleImageUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("images Files", "*.png", "*.jpg", "*.gif", "*.webp")
        );

        // selectedFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);

            selectedImageName = selectedFile.getName();
        }

        return true;
    }

    private void uploadImage(File selectedFile, String name) throws IOException{
        String targetDirectory = System.getProperty("user.dir") + "/images/products";
        File directory = new File(targetDirectory);
        if(!directory.exists()){
            directory.mkdir();
        }

        File targetFile = new File(directory, name);
        Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        System.out.println("File uploaded successfully to: " + targetFile.getAbsolutePath());
    }

    public void handleSubmit(ActionEvent event) {
        validateForm();

        try{
            uploadImage(selectedFile, selectedImageName);
        } catch (IOException e) {
            System.out.println("Failed to upload image: " + e.getMessage());
        }

        String _SKU = SKU.getText();
        int _WP_ID = Integer.parseInt(WP_ID.getText());
        String _productName = productName.getText();
        String _prouctNameVN = productNameVN.getText();
        String _productShortDescription = productShortDescription.getText();
        String _productDescription = productDescription.getText();
        String _productImageUrl = selectedImageName;

        Double _productStock = Double.parseDouble(productStock.getText());
        int _productTax= Integer.parseInt(productTax.getText());
        String _productBrand = productBrand.getText();

        Double _PquantityInBaseUnit = Double.parseDouble(PquantityInBaseUnit.getText());
        Double _PunitRegularPrice = Double.parseDouble(PunitRegularPrice.getText());
        Double _PunitSalePrice = Double.parseDouble(PunitSalePrice.getText());
        Double _PunitBuyPrice = Double.parseDouble(PunitBuyPrice.getText());

        Double _BquantityInBaseUnit = Double.parseDouble(BquantityInBaseUnit.getText());
        Double _BunitRegularPrice = Double.parseDouble(BunitRegularPrice.getText());
        Double _BunitSalePrice = Double.parseDouble(BunitSalePrice.getText());
        Double _BunitBuyPrice = Double.parseDouble(BunitBuyPrice.getText());

        Double _CquantityInBaseUnit = Double.parseDouble(CquantityInBaseUnit.getText());
        Double _CunitRegularPrice = Double.parseDouble(CunitRegularPrice.getText());
        Double _CunitSalePrice = Double.parseDouble(CunitSalePrice.getText());
        Double _CunitBuyPrice = Double.parseDouble(CunitBuyPrice.getText());

        List<ModelUnit> unitList = new ArrayList<ModelUnit>();
        ModelUnit P_modelUnit = new ModelUnit("piece", "Individual piece",_PquantityInBaseUnit, _PunitRegularPrice,_PunitSalePrice, _PunitBuyPrice);
        ModelUnit B_modelUnit = new ModelUnit("box", "Iox of 10 pieces",_BquantityInBaseUnit, _BunitRegularPrice,_BunitSalePrice, _BunitBuyPrice);
        ModelUnit C_modelUnit = new ModelUnit("carton", "Individual piece",_CquantityInBaseUnit, _CunitRegularPrice,_CunitSalePrice, _CunitBuyPrice);
        unitList.add(P_modelUnit);
        unitList.add(B_modelUnit);
        unitList.add(C_modelUnit);

        ModelProducts modelProducts = new ModelProducts(_SKU, _WP_ID, _productName, _prouctNameVN, _productDescription, _productShortDescription,
                _productImageUrl, _productStock, _productTax, _productBrand, unitList);

        productService.newProduct(modelProducts);

        Notification.showAlert(Alert.AlertType.INFORMATION, "Data Saved Successfully", "The Image has been uploaded.");

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean validateForm() {
        String errorMessage = "";

        try {
            Integer.parseInt(WP_ID.getText());
        } catch (NumberFormatException e) {
            errorMessage += "WPID must be a valid integer.\n";
        }

        if (productName.getText() == null || productName.getText().isEmpty()) {
            errorMessage += "Product name is required .\n";
        }

        if (productNameVN.getText() == null || productNameVN.getText().isEmpty()) {
            errorMessage += "Product name Vietnam is required .\n";
        }

        if (productNameVN.getText() == null || productNameVN.getText().isEmpty()) {
            errorMessage += "Product name Vietnam is required .\n";
        }

        if(!errorMessage.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Form Validation Error", errorMessage);
            return false;
        }

        return true;
    }

}
