package com.gp11.flightapp.model;
/**
 * Represents a reservation in the flight system.
 * Contains unique ID for the reservation and the associated
 * user and flight.
 */
public class Reservation {
    private String id;
    private String userId;
    private String flightId;
    /**
     * Default constructor.
     * Initializes a reservation with no attributes.
     */
    public Reservation() {};
    /**
     * Creates a reservation with string inputs.
     * @param id String of ID.
     * @param userId String of User ID.
     * @param flightId String of Flight ID.
     */
    public Reservation(String id, String userId, String flightId) {
        this.id = id;
        this.userId = userId;
        this.flightId = flightId;
    }
    /**
     * Creates a resrvation with string inputs; Leaves reservation ID null.
     * @param userId String of User ID.
     * @param flightId String of Flight ID.
     */
    public Reservation(String userId, String flightId) {
        this.id = null;
        this.userId = userId;
        this.flightId = flightId;
    }
    /**
     * Creates a reservation with ID string and user and flight objects.
     * @param id Reservation ID.
     * @param user User object.
     * @param flight Flight object.
     */
    public Reservation(String id, User user, Flight flight) {
        if (user == null || flight == null) {
            throw new IllegalArgumentException("Object arguments must not be null.");
        }
        this.id = id;
        this.userId = user.getId();
        this.flightId = flight.getId();
    }
    /**
     * Creates a reservation using user and flight objects. ID is left null.
     * @param user User object.
     * @param flight Flight object.
     */
    public Reservation(User user, Flight flight) {
        if (user == null || flight == null) {
            throw new IllegalArgumentException("Object arguments must not be null.");
        }
        this.id = null;
        this.userId = user.getId();
        this.flightId = flight.getId();
    }
    /**
     * Sets reservation ID.
     * @param id Reservation ID.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Sets User ID.
     * @param userId User ID.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * Sets User ID using user object.
     * Business rule: User object cannot be null.
     * @param user User object.
     */
    public void setUserId(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User argument cannot be null.");
        }
        this.userId = user.getId();
    }
    /**
     * Set Flight ID.
     * @param flightId Flight ID.
     */
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    
    }
    /**
     * Set Flight ID using flight object.
     * Business rule: Flight object cannot be null.
     * @param flight Flight object.
     */
    public void setFlightId(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight argument cannot be null.");
        }
        this.flightId = flight.getId();
    }
    /**
     * Gets ID.
     * @return Reservation ID.
     */
    public String getId() {
        return id;
    }
    /**
     * Gets User ID.
     * @return User ID.
     */
    public String getUserId() {
        return userId;
    }
    /**
     * Gets Flight ID.
     * @return Flight ID.
     */
    public String getFlightId() {
        return flightId;
    }
}
