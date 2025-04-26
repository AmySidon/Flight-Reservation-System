package com.gp11.flightapp.dao;

import java.time.LocalDate;
import java.util.List;

import com.gp11.flightapp.exceptions.DeletionRestrictedException;
import com.gp11.flightapp.exceptions.DuplicateEmailException;
import com.gp11.flightapp.model.Flight;
import com.gp11.flightapp.model.Reservation;
import com.gp11.flightapp.model.User;
import com.mongodb.client.MongoDatabase;

public class BookingService {
    private final UserDAOimpl userDAO;
    private final FlightDAOimpl flightDAO;
    private final ReservationDAOimpl reservationDAO;

    public BookingService(MongoDatabase db) {
        this.reservationDAO = new ReservationDAOimpl(db);
        this.userDAO = new UserDAOimpl(db);
        this.flightDAO = new FlightDAOimpl(db);
        
    }
    public UserDAOimpl getUserDAO() {
        return userDAO;
    }
    public FlightDAOimpl getFlightDAO() {
        return flightDAO;
    }
    public ReservationDAOimpl getReservationDAO() {
        return reservationDAO;
    }
    // USER CRUD OPERATIONS
    // Takes a User object and adds it to the database, generating a unique ID.
    // If a user with the email is already in the database, it throws a DuplicateEmailException.
    public void addUser(User user) throws DuplicateEmailException {
        userDAO.create(user);
    }
    // Takes an ID string and returns the corresponding User object from the database
    public User searchUser(String id) {
        return userDAO.read(id);
    }
    // Takes an email string and returns the corresponding User object
    public User searchUserEmail(String email) {
        return userDAO.readByEmail(email);
    }
    // Returns a all Users from the database as a lists of User objects
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    // Takes a user object with updated information and updates the corresponding
    // user in the database.
    public void updateUser(User user) throws DuplicateEmailException {
        userDAO.update(user);
    }
    // Takes an ID for a user as a string and deletes it from the database.
    // If the user is associated with an exsiting reservation(s), the method
    // throws a DeletionRestrictedException.
    public void deleteUser(String id) throws DeletionRestrictedException {
        User user = userDAO.read(id);
        if(reservationDAO.readByUserId(user.getId()) != null) {
            throw new DeletionRestrictedException("user", id);
        }
        userDAO.delete(id);
    }
    // FLIGHT CRUD OPERATIONS
    // Take a Flight object and creates a corresponding document in the database.
    public void scheduleFlight(Flight flight) {
        flightDAO.create(flight);
    }
    // Takes the ID of a flight as a string and returns the Flight object from the
    // database.
    public Flight readFlight(String id) {
        return flightDAO.read(id);
    }
    //Overloads the previous method to take two dates, destination and origin airports. Returns
    //All flight objects that match.
    public List<Flight> searchFlights(LocalDate startDate, LocalDate endDate, String origin, String destination) {
        return flightDAO.readByDateAndAirport(startDate, endDate, origin, destination);
    }
    // Takes a start date and end date and returns a list of all flights between the two.
    public List<Flight> searchFlightsByDate(LocalDate startDate, LocalDate endDate) {
        return flightDAO.readByDateRange(startDate, endDate);
    }
    // Takes the origin airport as a string, and returns all flights leaving from there.
    public List<Flight> searchFlightsByOrigin(String origin) {
        return flightDAO.readByOrigin(origin);
    }
    // Takes the destination airport as a string, and returns all flights arriving there.
    public List<Flight> searchFlightsByDestination(String destination) {
        return flightDAO.readByDestination(destination);
    }
    // Returns all flights in database in the form of a list of Flight objects.
    public List<Flight> getAllFlights() {
        return flightDAO.getAll();
    }
    // Takes a Flight object and updates the document in the database with the corresponding
    // ID.
    public void updateFlight(Flight flight) {
        flightDAO.update(flight);
    }
    // Takes the ID of a flight as a string and removes the corresponding flight from the
    // database. If any reservations have been made with the flight, the method
    // throws a DeletionRestrictedException.
    public void cancelFlight(String id) throws DeletionRestrictedException {
        Flight flight = flightDAO.read(id);
        if (reservationDAO.read(flight.getId()) != null) {
            throw new DeletionRestrictedException("flight", id);
        }
        flightDAO.delete(id);
    }
    // RESERVATION CRUD OPERATIONS
    // Takes a reservation object and creates corresponding document in the database.
    public void bookReservation(Reservation reservation) {
        reservationDAO.create(reservation);
    }
    // Overloads the previous method to take a user and flight object. A corresponding
    // reservation is added to the database.
    public void bookReservation(User user, Flight flight) {
        reservationDAO.create(new Reservation(user, flight));
    }
    // Takes the ID of a reservation as a string and returns the resrvation object from
    // the database.
    public Reservation findReservation(String id) {
        return reservationDAO.read(id);
    }
    // Takes the ID of a user and returns all associated reservations in a list.
    public List<Reservation> findReservationsByUserId(String id) {
        return reservationDAO.readByUserId(id);
    }
    // Takes the ID of a flight and returns all associated resrvations in a list.
    public List<Reservation> findReservationsByFlightId(String id) {
        return reservationDAO.readByFlightId(id);
    }
    // Returns all reservations in the database in a list.
    public List<Reservation> getAllReservations() {
        return reservationDAO.getAll();
    }
    // Takes a reservation and updates the reservation in the database that has the
    // corresponding ID.
    public void updateReservation(Reservation reservation) {
        reservationDAO.update(reservation);
    }
    // Takes the ID of a reservation and deletes the corresponding reservation from
    // the database.
    public void cancelReservation(String id) {
        reservationDAO.delete(id);
    }

}
