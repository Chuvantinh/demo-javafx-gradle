package com.orders.vantinh;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private Button btnProducts;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnOrders;

    @FXML
    private AnchorPane anchorOrders;

    @FXML
    private AnchorPane anchorProducts;

    @FXML
    private AnchorPane anchorCustomers;

    @FXML
    private AnchorPane anchorSettings;

    @FXML
    private TableView<ModelOrders> orderTableView;

    // Reference to MongoDB database
    private  MongoCollection<Document> orderCollection;

    @FXML
    private TableColumn<ModelOrders, String> orderID;

    @FXML
    private TableColumn<ModelOrders, Integer> orderNumber;

    @FXML
    private TableColumn<ModelOrders, String> orderDate;

    @FXML
    private TableColumn<ModelOrders, Long> customerID;

    @FXML
    private TableColumn<ModelOrders, Double> orderAmount;

    @FXML
    private TableColumn<ModelOrders, String> orderPayment;

    @FXML
    private TableColumn<ModelOrders, String> orderStatus;

    // ObservableList to store the data for the TableView
    private ObservableList<ModelOrders> orderList = FXCollections.observableArrayList();



    // Action for Order TableView
    @FXML
    void ActionLoadOrders(ActionEvent event) {
        orderID.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("id"));
        orderNumber.setCellValueFactory(new PropertyValueFactory<ModelOrders, Integer>("orderNumber"));
        orderDate.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("orderDate"));
        customerID.setCellValueFactory(new PropertyValueFactory<ModelOrders, Long>("customerID"));
        orderAmount.setCellValueFactory(new PropertyValueFactory<ModelOrders, Double>("orderAmount"));
        orderPayment.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("orderPayment"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<ModelOrders, String>("orderStatus"));

        orderCollection = DBConnection.getCollection("javafx-order-management","orders");

        MongoCursor<Document> cursor = orderCollection.find().iterator();
        List<ModelOrders> orders = new ArrayList<>();

        try{
            while(cursor.hasNext()){
                Document doc = cursor.next();

                ObjectId id = doc.getObjectId("_id");
                Integer orderNumber = doc.getInteger("orderNumber");
                String orderDate = doc.getString("orderDate");
                Long customerID = (Long)doc.getLong("customerID");
                Double orderAmount = doc.getDouble("orderAmount");
                String orderPayment = doc.getString("orderPayment");
                String orderStatus = doc.getString("orderStatus");

                orders.add(new ModelOrders( id, orderNumber, orderDate, customerID, orderAmount, orderPayment, orderStatus));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }

        // Add the list of orders to the TableView
        orderList.addAll(orders);
        this.orderTableView.setItems(orderList);
    }

    @FXML
    void ActionNewOrder(ActionEvent event) {

    }

    @FXML
    void ActionEditOrder(ActionEvent event) {

    }

    @FXML
    void ActionRemoveOrder(ActionEvent event) {

    }

    @FXML
    public void switchAnchorPane(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        switch (clickedButton.getId()) {
            case "btnOrders":
                System.out.println("Orders");
                anchorOrders.setVisible(true);
                anchorProducts.setVisible(false);
                anchorCustomers.setVisible(false);
                anchorSettings.setVisible(false);
                break;
            case "btnProducts":
                anchorProducts.setVisible(true);
                anchorCustomers.setVisible(false);
                anchorOrders.setVisible(false);
                anchorSettings.setVisible(false);
                break;
            case "btnCustomers":
                anchorCustomers.setVisible(true);
                anchorOrders.setVisible(false);
                anchorSettings.setVisible(false);
                anchorProducts.setVisible(false);
                break;
            case "btnSettings":
                anchorSettings.setVisible(true);
                anchorOrders.setVisible(false);
                anchorProducts.setVisible(false);
                anchorCustomers.setVisible(false);
                break;
            default:
                anchorOrders.setVisible(true);
                anchorProducts.setVisible(false);
                anchorCustomers.setVisible(false);
                anchorSettings.setVisible(false);
                break;
        }
    }


}
