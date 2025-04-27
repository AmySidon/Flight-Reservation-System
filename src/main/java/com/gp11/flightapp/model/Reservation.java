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
        if (user == null || flight == null) {
            throw new IllegalArgumentException("Object arguments must not be null.");
        }
        this.id = id;
        this.userId = user.getId();
        this.flightId = flight.getId();
    }

    public Reservation(User user, Flight flight) {
        if (user == null || flight == null) {
            throw new IllegalArgumentException("Object arguments must not be null.");
        }
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
        if (user == null) {
            throw new IllegalArgumentException("User argument cannot be null.");
        }
        this.userId = user.getId();
    }
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
    public void setFlightId(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight argument cannot be null.");
        }
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
