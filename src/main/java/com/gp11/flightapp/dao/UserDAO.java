package com.gp11.flightapp.dao;

import com.gp11.flightapp.model.User;
import com.gp11.flightapp.exceptions.DuplicateEmailException;

public interface UserDAO extends GenericDAO<User> {
    public User readByEmail(String email);

    @Override
    void create(User user) throws DuplicateEmailException;
    @Override
    void update(User user) throws DuplicateEmailException;

}
