package com.gp11.flightapp.dao;
import java.util.List;

import com.gp11.flightapp.exceptions.DAOException;

public interface GenericDAO<T> {
    void create(T t) throws DAOException;
    void update(T t) throws DAOException;
    void delete(String id) throws DAOException;
    T read(String id);
    List<T> getAll();
}
