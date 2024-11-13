package com.orders.vantinh.models;

import org.bson.types.ObjectId;

import java.util.Date;

public class ModelCustomers {
    public final ObjectId id;
    public final String firstname;
    public final String lastname;
    public final String email;
    public final String phone;
    public final String address;
    public final Date accountCreated;
    public final EnumCustomerStatus status;

    public ModelCustomers(String firstname, String lastname, String email, String phone, String address, Date accountCreated, EnumCustomerStatus status) {
        this.id = new ObjectId();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.accountCreated = accountCreated;
        this.status = status;
    }

    public ObjectId getID() {
        return id;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getAddress(){
        return address;
    }
}
