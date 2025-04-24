package com.gp11.flightapp.utils;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static final String CONNECTION_URI = "mongodb+srv://fmb37:group16@g16-project.rl4rjyj.mongodb.net/?retryWrites=true&w=majority&appName=g16-project";
    private static final String DB_NAME = "flight_reservation_db";

    private static MongoClient mongoClient;

    public static MongoClient getClient() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_URI);
        }
        return mongoClient;
    }

    public static MongoDatabase getDatabase() {
        return getClient().getDatabase(DB_NAME);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
