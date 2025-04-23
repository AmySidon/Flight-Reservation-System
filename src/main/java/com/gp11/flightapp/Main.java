package com.gp11.flightapp;

import com.gp11.flightapp.utils.MongoUtil;
import com.mongodb.client.MongoDatabase;

public class Main {
    public static void main(String[] args) {
        System.out.println("Did you see that ludicrous display last night?");
        MongoDatabase db = MongoUtil.getDatabase();
        System.out.println("Connected to database: " + db.getName());
    }
}