package com.gp11.flightapp.model;
import java.time.LocalDate;

public class Flight {
    private String id;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDate date;

    public Flight() {}

    public Flight(String id, String departureAirport, String arrivalAirport, LocalDate date) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.date = date;
    }

    public Flight(String departureAirport, String arrivalAirport, LocalDate date) {
        this.id = null;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.date = date;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }
    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getId() {
        return id;
    }
    public String getDepartureAirport() {
        return departureAirport;
    }
    public String getArrivalAirport() {
        return arrivalAirport;
    }
    public LocalDate getDate() {
        return date;
    }
    @Override
    public String toString() {
        return "Departed from " + departureAirport + ", arriving at " + arrivalAirport + " on " + date;
    }
}
