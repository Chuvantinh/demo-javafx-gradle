package com.orders.vantinh.controllers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.orders.vantinh.App;
import com.orders.vantinh.controllers.Abstract.AbstractTableController;
import com.orders.vantinh.controllers.Interfaces.TableViewConfigurable;
import com.orders.vantinh.dao.DBConnection;
import com.orders.vantinh.models.ModelOrders;
import com.orders.vantinh.services.CustomerService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AbstractTableController<ModelOrders> implements TableViewConfigurable<ModelOrders> {
    @FXML
    private TableView<ModelOrders> tableView;

    @FXML
    private Button btnComeback;

    @FXML
    private TableColumn<ModelOrders, String> orderID;

    @FXML
    private TableColumn<ModelOrders, String> orderDate;

    @FXML
    private TableColumn<ModelOrders, String> customerID;

    @FXML
    private TableColumn<ModelOrders, Double> orderAmount;

    @FXML
    private TableColumn<ModelOrders, String> orderPayment;

    @FXML
    private TableColumn<ModelOrders, String> orderStatus;

    // ObservableList to store the data for the TableView
    private final ObservableList<ModelOrders> orderList = FXCollections.observableArrayList();

    private CustomerService customerService;

    public void ActionBack(){
        try{
            App.setRoot("main-view");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        this.tableViewAbs = tableView;
        // Set multi-row selection mode for TableView
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setReponsiveColumnWidth();

        customerService = new CustomerService();

        // Reference to MongoDB database
        MongoCollection<Document> mongoCollectionDocument = DBConnection.getCollection("javafx-order-management", "orders");

        setupColumn();
        setupTableView();

        MongoCursor<Document> cursor = mongoCollectionDocument.find().iterator();
        List<ModelOrders> orders = new ArrayList<>();
        try{
            while(cursor.hasNext()){
                Document doc = cursor.next();

                ObjectId orderID = doc.getObjectId("_id");
                String orderDate = doc.getString("orderDate");
                String customerID = doc.getString("customerID");
                double orderAmount = Double.parseDouble(doc.getString("orderAmount"));
                String orderPayment = doc.getString("orderPayment");
                String orderStatus = doc.getString("orderStatus");

                orders.add(new ModelOrders( orderID, orderDate, customerID, orderAmount, orderPayment, orderStatus));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }

        // Add the list of orders to the TableView
        orderList.addAll(orders);
        tableView.setItems(orderList);
    }

    private void setupColumn(){
        orderID.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("id"));
        orderID.setPrefWidth(150);

        orderDate.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("orderDate"));

        customerID.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("customerID"));
        customerID.setCellValueFactory( customer-> {
            String customerID = customer.getValue().getCustomerID();
            String fullName = customerService.getCustomerById(customerID);
            return new SimpleStringProperty(fullName);
        });

        orderAmount.setCellValueFactory(new PropertyValueFactory<ModelOrders, Double>("orderAmount"));
        orderPayment.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("orderPayment"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("orderStatus"));
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

    private void setupTableView() {
        tableView.setEditable(true);

        // Set anchor constraints for the TableView to fill the AnchorPane
        AnchorPane.setTopAnchor(tableView, 200.0);
        AnchorPane.setBottomAnchor(tableView, 50.0);
        AnchorPane.setLeftAnchor(tableView, 50.0);
        AnchorPane.setRightAnchor(tableView, 50.0);
    }
}