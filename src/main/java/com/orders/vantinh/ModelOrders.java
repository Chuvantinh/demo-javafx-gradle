package com.orders.vantinh;

import org.bson.types.ObjectId;

public class ModelOrders {
    private ObjectId ID;
    private int orderNumber;
    private String orderDate;
    private Long customerID;
    private double orderAmount;
    private String orderPayment;
    private String orderStatus;


    public ModelOrders(ObjectId ID, int orderNumber, String orderDate, Long customerID, double orderAmount, String orderPayment, String orderStatus) {
        this.ID = ID;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.customerID = customerID;
        this.orderAmount = orderAmount;
        this.orderPayment = orderPayment;
        this.orderStatus = orderStatus;

    }
    public ObjectId getId() {return ID;}

    public void setId(ObjectId id) {this.ID = ID;}

    public int getOrderNumber() { return orderNumber;}

    public void setOrderNumber(int orderNumber){this.orderNumber = orderNumber;}

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate){this.orderDate = orderDate;}

    public Long getCustomerID() { return customerID; }
    public void setCustomerID(Long customerID){this.customerID=customerID;}

    public double getOrderAmount() { return orderAmount; }
    public void setOrderAmount(double orderAmount){this.orderAmount=orderAmount;}

    public String getOrderPayment() { return orderPayment; }
    public void setOrderPayment(String orderPayment){this.orderPayment=orderPayment;}

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus){this.orderStatus=orderStatus;}
}
