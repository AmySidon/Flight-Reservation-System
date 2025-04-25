package com.gp11.flightapp.dao;

import com.gp11.flightapp.model.User;

public interface UserDAO extends GenericDAO<User> {
    public User readByEmail(String email);
}
