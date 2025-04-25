package com.gp11.flightapp.dao;

import java.util.List;

import com.gp11.flightapp.model.Reservation;

public interface ReservationDAO extends GenericDAO<Reservation>{
    public List<Reservation> readByUserId(String id);
    public List<Reservation> readByFlightId(String id);
}
