package com.gp11.flightapp;
import java.util.List;

public interface CrudRepository<T> {
    void insert(T t);
    void update(T t);
    void delete(String id);
    T findById(String id);
    List<T> findAll();
}
