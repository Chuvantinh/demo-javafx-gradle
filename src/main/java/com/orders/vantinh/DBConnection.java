
package com.orders.vantinh;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;


public class DBConnection {
    private static String CONNECTION_URI = "mongodb://localhost:27017";
    private static MongoClient mongoClient;

    // Private constructor to prevent direct instantiation
    private DBConnection() {}

    public static MongoClient getMongoClient(){
        if (mongoClient == null) {
            // Initialize the MongoClient if it's not already done
            mongoClient = MongoClients.create(CONNECTION_URI);
        }
        return mongoClient;
    }

    // Method to close the MongoClient connection
    private void close(){
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    // Get MongoDatabase instance
    public static MongoDatabase getDatabase(String dbName){
        return getMongoClient().getDatabase(dbName);
    }

    // Generic method to get any collection by name
    public static MongoCollection<Document> getCollection(String dbName, String collectionName){
        return getDatabase(dbName).getCollection(collectionName);
    }

}

