package com.gp11.flightapp.model;

public class Reservation {
    private String id;
    private String userId;
    private String flightId;

    public Reservation() {};

    public Reservation(String id, String userId, String flightId) {
        this.id = id;
        this.userId = userId;
        this.flightId = flightId;
    }
    public Reservation(String userId, String flightId) {
        this.id = null;
        this.userId = userId;
        this.flightId = flightId;
    }

    public Reservation(String id, User user, Flight flight) {
        this.id = id;
        this.userId = user.getId();
        this.flightId = flight.getId();
    }

    public Reservation(User user, Flight flight) {
        this.id = null;
        this.userId = user.getId();
        this.flightId = flight.getId();
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUserId(User user) {
        this.userId = user.getId();
    }
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
    public void setFlightId(Flight flight) {
        this.flightId = flight.getId();
    }
    public String getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public String getFlightId() {
        return flightId;
    }
}
