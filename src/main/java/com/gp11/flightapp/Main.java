package com.gp11.flightapp;

import com.gp11.flightapp.dao.UserDAO;
import com.gp11.flightapp.model.User;
import com.gp11.flightapp.utils.MongoUtil;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO(MongoUtil.getDatabase());

        for (User u : userDAO.getAll()) {
            System.out.println(u.toString());
        }

        MongoUtil.close();
    }
}