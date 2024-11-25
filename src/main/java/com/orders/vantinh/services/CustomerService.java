package com.orders.vantinh.services;

import com.mongodb.client.MongoCollection;
import com.orders.vantinh.dao.DBConnection;
import com.orders.vantinh.models.EnumCustomerStatus;
import com.orders.vantinh.models.ModelCustomers;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private Map<String, ModelCustomers> customerMap = new HashMap<>();
    private final MongoCollection<Document> customerCollection;

    public CustomerService(){
        customerCollection = DBConnection.getCollection("javafx-order-management", "customers");
        loadAllCustomers();
    }

    public void loadAllCustomers() {
        for (Document customer : customerCollection.find()) {
            ObjectId customerId = customer.getObjectId("_id");
            String firstName = customer.getString("firstname");
            String lastName = customer.getString("lastname");
            String email = customer.getString("email");
            String phone = customer.getString("phone");
            String address = customer.getString("address");
            Date accountCreated = null;
            EnumCustomerStatus status = EnumCustomerStatus.valueOf(customer.getString("status"));

            ModelCustomers modelCustomers = new ModelCustomers(firstName, lastName, email, phone, address, accountCreated, status);
            customerMap.put(customerId.toString(), modelCustomers);
        }
    }

    public String getCustomerById(String customerId) {
        ModelCustomers modelCustomers = customerMap.get(customerId);
        return modelCustomers.getFirstname() + " " + modelCustomers.getLastname();
    }
}
