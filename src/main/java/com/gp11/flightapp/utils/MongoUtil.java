package com.gp11.flightapp.utils;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static final String CONNECTION_URI = "mongodb+srv://fmb37:group16@g16-project.rl4rjyj.mongodb.net/?retryWrites=true&w=majority&appName=g16-project";
    private static final String DB_NAME = "flight_reservation_db";

    private static MongoClient mongoClient = null;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_URI);
        }
        return mongoClient.getDatabase(DB_NAME);
    }
}
