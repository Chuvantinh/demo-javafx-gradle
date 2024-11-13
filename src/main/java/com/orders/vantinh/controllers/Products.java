package com.orders.vantinh.controllers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.orders.vantinh.App;
import com.orders.vantinh.controllers.Abstract.AbstractTableController;
import com.orders.vantinh.dao.DBConnection;
import com.orders.vantinh.models.ModelProducts;
import com.orders.vantinh.models.ModelUnit;
import com.orders.vantinh.services.ImageCell;
import com.orders.vantinh.services.ProductService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.bson.Document;
import org.bson.types.ObjectId;
import javafx.scene.control.SelectionMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Products  extends AbstractTableController<ModelProducts> {
    @FXML
    private TableView<ModelProducts> tableView;

    @FXML
    private Button btnComeback;

    @FXML
    private TableColumn<ModelProducts, String> ID;

    @FXML
    private TableColumn<ModelProducts, String> SKU;

    @FXML
    private TableColumn<ModelProducts, String> productName;

    @FXML
    private TableColumn<ModelProducts, String> productNameVN;

    @FXML
    private TableColumn<ModelProducts, String> productDescription;

    @FXML
    private TableColumn<ModelProducts, String> productShortDescription;

    @FXML
    private TableColumn<ModelProducts, Double> productStock;

    @FXML
    private TableColumn<ModelProducts, String> productImageUrl;

    @FXML
    private TableColumn<ModelProducts, String> productUnits;

    ProductService productService = new ProductService();

    // ObservableList to store the data for the TableView
    private final ObservableList<ModelProducts> productsList = FXCollections.observableArrayList();

    public void ActionBack(){
        try{
            App.setRoot("main-view");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        //set width for all columns
        this.tableViewAbs = tableView;
        // Set multi-row selection mode for TableView
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setReponsiveColumnWidth();


        // Reference to MongoDB database
        MongoCollection<Document> mongoCollectionDocument = DBConnection.getCollection("javafx-order-management", "products");
        MongoCursor<Document> cursor = mongoCollectionDocument.find().iterator();

        List<ModelProducts> products = new ArrayList<>();
        try{
            while(cursor.hasNext()){
                Document doc = cursor.next();

                ObjectId ID = doc.getObjectId("_id");
                String SKU = String.valueOf(doc.getDouble("SKU"));
                String WPID = String.valueOf(doc.getDouble("WPID"));

                String productName = doc.getString("productName");
                String productNameVN = doc.getString("productNameVN");
                String productDescription = doc.getString("productDescription");
                String productShortDescription = doc.getString("productShortDescription");
                String productImageUrl = doc.getString("productImageUrl");
                double productStock = doc.getDouble("productStock");

                List<Document> productUnits = doc.getList("units", Document.class);
                List<ModelUnit> unitsList = ProductService.convertToModelUnits(productUnits);

                products.add(new ModelProducts( ID, SKU, WPID, productName, productNameVN, productDescription, productShortDescription, productImageUrl, productStock, unitsList));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }

        // Add the list of orders to the TableView
        productsList.addAll(products);
        tableView.setItems(productsList);

        setupColumn();
        setupTableView();
    }


    private void setupColumn(){
        ID.setCellValueFactory(new PropertyValueFactory<ModelProducts, String>("id"));

        productImageUrl.setCellValueFactory( cellData -> new SimpleStringProperty(cellData.getValue().getProductImageUrl()));
        productImageUrl.setCellFactory(param -> new ImageCell<>());

        SKU.setCellValueFactory(new PropertyValueFactory<ModelProducts, String>("SKU"));
        productName.setCellValueFactory(new PropertyValueFactory<ModelProducts, String>("productName"));
        productNameVN.setCellValueFactory(new PropertyValueFactory<ModelProducts, String>("productNameVN"));

        productDescription.setCellValueFactory(new PropertyValueFactory<ModelProducts, String>("productDescription"));
        productShortDescription.setCellValueFactory(new PropertyValueFactory<ModelProducts, String>("productShortDescription"));
        productStock.setCellValueFactory(new PropertyValueFactory<ModelProducts, Double>("productStock"));

        // Units column displaying the formatted string
        productUnits.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getUnitsDisplay());
        });

    }

    // Action for Order TableView
    @FXML
    private void ActionLoad(ActionEvent event) {
        tableView.refresh();
    }

    @FXML
    private void ActionNew(ActionEvent event) {
        loadView("main-view");
    }

    @FXML
    private void ActionEdit(ActionEvent event) {
        loadView("main-view");
    }

    @FXML
    private void ActionRemove(ActionEvent event) {
        loadView("main-view");
    }

    @FXML
    private void handleImportButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            // Call your method to import data from the selected file
            productService.importExcelData(file);
        }
    }

    @FXML
    private void handleExportButtonAction(){
        ObservableList<ModelProducts> selectedItems = tableView.getSelectionModel().getSelectedItems();

        if(!selectedItems.isEmpty()){
            productService.exportDataToExcel(selectedItems);
        }else{
            productService.exportDataToExcel(tableView.getItems());
        }

    }

    private void setupTableView() {
        tableView.setEditable(true);

        // Set anchor constraints for the TableView to fill the AnchorPane
        AnchorPane.setTopAnchor(tableView, 200.0);
        AnchorPane.setBottomAnchor(tableView, 50.0);
        AnchorPane.setLeftAnchor(tableView, 50.0);
        AnchorPane.setRightAnchor(tableView, 50.0);
    }
}