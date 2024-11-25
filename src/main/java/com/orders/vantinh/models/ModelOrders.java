package com.orders.vantinh.models;

import com.orders.vantinh.controllers.Products;
import org.bson.types.ObjectId;

public class ModelOrders {
    private ObjectId ID;
    private String orderDate;
    private String customerID;
    private Products products;
    private double orderAmount;
    private String orderPayment;
    private String orderStatus;


    public ModelOrders(ObjectId ID, String orderDate, String customerID, double orderAmount, String orderPayment, String orderStatus) {
        this.ID = ID;
        this.orderDate = orderDate;
        this.customerID = customerID;
        this.orderAmount = orderAmount;
        this.orderPayment = orderPayment;
        this.orderStatus = orderStatus;
    }
    public ObjectId getId() {return ID;}
    public void setId(ObjectId ID) {this.ID = ID;}

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate){this.orderDate = orderDate;}

    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID){this.customerID=customerID;}

    public double getOrderAmount() { return orderAmount; }
    public void setOrderAmount(double orderAmount){this.orderAmount=orderAmount;}

    public String getOrderPayment() { return orderPayment; }
    public void setOrderPayment(String orderPayment){this.orderPayment=orderPayment;}

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus){this.orderStatus=orderStatus;}
}
