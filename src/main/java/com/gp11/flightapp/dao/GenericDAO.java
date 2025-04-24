package com.gp11.flightapp.dao;
import java.util.List;

public interface GenericDAO<T> {
    void create(T t);
    void update(T t);
    void delete(String id);
    T read(String id);
    List<T> getAll();
}
