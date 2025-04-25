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
    public void addUser(User user) throws DuplicateEmailException {
        userDAO.create(user);
    }
    public User searchUser(String id) {
        return userDAO.read(id);
    }
    public User searchUserEmail(String email) {
        return userDAO.readByEmail(email);
    }
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    public void updateUser(User user) throws DuplicateEmailException {
        userDAO.update(user);
    }
    public void deleteUser(String id) throws DeletionRestrictedException {
        User user = userDAO.read(id);
        if(reservationDAO.readByUserId(user.getId()) != null) {
            throw new DeletionRestrictedException("user", id);
        }
        userDAO.delete(id);
    }
    // FLIGHT CRUD OPERATIONS
    public void scheduleFlight(Flight flight) {
        flightDAO.create(flight);
    }
    public Flight readFlight(String id) {
        return flightDAO.read(id);
    }
    public List<Flight> searchFlightByDate(LocalDate startDate, LocalDate endDate) {
        return flightDAO.readByDateRange(startDate, endDate);
    }
    public List<Flight> searchFlightByOrigin(String origin) {
        return flightDAO.readByOrigin(origin);
    }
    public List<Flight> searchFlightByDestination(String destination) {
        return flightDAO.readByDestination(destination);
    }
    public List<Flight> getAllFlights() {
        return flightDAO.getAll();
    }
    public void updateFlight(Flight flight) {
        flightDAO.update(flight);
    }
    public void cancelFlight(String id) throws DeletionRestrictedException {
        Flight flight = flightDAO.read(id);
        if (reservationDAO.read(flight.getId()) != null) {
            throw new DeletionRestrictedException("flight", id);
        }
        flightDAO.delete(id);
    }
    // RESERVATION CRUD OPERATIONS
    public void bookReservation(Reservation reservation) {
        reservationDAO.create(reservation);
    }
    public Reservation findReservation(String id) {
        return reservationDAO.read(id);
    }
    public List<Reservation> findReservationsByUserId(String id) {
        return reservationDAO.readByUserId(id);
    }
    public List<Reservation> findReservationsByFlightId(String id) {
        return reservationDAO.readByFlightId(id);
    }
    public List<Reservation> getAllReservations() {
        return reservationDAO.getAll();
    }
    public void updateReservation(Reservation reservation) {
        reservationDAO.update(reservation);
    }
    public void cancelReservation(String id) {
        reservationDAO.delete(id);
    }

}
