package com.gp11.flightapp.dao;

import java.time.LocalDate;
import java.util.List;

import com.gp11.flightapp.model.Flight;

public interface FlightDAO extends GenericDAO<Flight> {
    public List<Flight> readByDateRange(LocalDate startDate, LocalDate endDate);
    public List<Flight> readByOrigin(String origin);
    public List<Flight> readByDestination(String destination);
    public List<Flight> readByDateAndAirport(LocalDate startDate, LocalDate endDate, String origin, String destination);

}
