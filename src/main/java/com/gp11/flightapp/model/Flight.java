package com.gp11.flightapp.model;
import java.time.LocalDate;
/**
 * Represents a flight using a unique ID, an origin, destination, and date.
 * For simplicity, we assume that each flight departs and arrives on the same day.
 */
public class Flight {
    private String id;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDate date;
    /**
     * Empty constructor.
     * Does nothing.
     */
    public Flight() {}
    /**
     * Constructs flight with given ID, departure and arrival airports, and a date.
     * @param id Flight ID.
     * @param departureAirport Departure Airport.
     * @param arrivalAirport Arrival Airport.
     * @param date Flight date.
     */
    public Flight(String id, String departureAirport, String arrivalAirport, LocalDate date) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.date = date;
    }
    /**
     * Constructs flight with given departure and arrival airports and a date.
     * @param departureAirport Departure Airport.
     * @param arrivalAirport Arrival Airport.
     * @param date Flight Date.
     */
    public Flight(String departureAirport, String arrivalAirport, LocalDate date) {
        this.id = null;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.date = date;
    }
    /**
     * Sets ID.
     * @param id Flight ID.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Sets departure airport.
     * Business rule: Departure airport cannot be empty or null.
     * @throws IllegalArgumentException if argument is empty or null.
     */
    public void setDepartureAirport(String departureAirport) {
        if(departureAirport == null || departureAirport.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport string cannot by empty or null");
        }
        this.departureAirport = departureAirport;
    }
    /**
     * Sets arrival airport.
     * Business rule: Arrival airport cannot be empty or null.
     * @throws IllegalArgumentException if argument is empty or null.
     */
    public void setArrivalAirport(String arrivalAirport) {
        if(arrivalAirport == null || arrivalAirport.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport string cannot by empty or null");
        }
        this.arrivalAirport = arrivalAirport;
    }
    /**
     * Sets flight date.
     * @param date Flight date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    /**
     * Gets flight ID.
     */
    public String getId() {
        return id;
    }
    /**
     * Gets departure airport.
     * @return Departure airport.
     */
    public String getDepartureAirport() {
        return departureAirport;
    }
    /**
     * gets arrival airprt.
     * @return Arrival airport.
     */
    public String getArrivalAirport() {
        return arrivalAirport;
    }
    /**
     * Gets flight date.
     * @return Flight date.
     */
    public LocalDate getDate() {
        return date;
    }
    /**
     * Produces string of object with relevant info.
     */
    @Override
    public String toString() {
        return departureAirport + " -> " + arrivalAirport + " || " + date;
    }
}
