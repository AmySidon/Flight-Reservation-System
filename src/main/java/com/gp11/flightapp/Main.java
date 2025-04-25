package com.gp11.flightapp;

import java.util.List;

import com.gp11.flightapp.dao.BookingService;
import com.gp11.flightapp.model.Flight;
import com.gp11.flightapp.model.Reservation;
import com.gp11.flightapp.model.User;
import com.gp11.flightapp.utils.MongoUtil;

public class Main {
    public static void main(String[] args) {
        BookingService bookingApp = new BookingService(MongoUtil.getDatabase());
        User user = bookingApp.searchUserEmail("spac3b1k3r@blackmesa.com");
        List<Flight> flights = bookingApp.searchFlightByOrigin("AUS");
        Flight flight = flights.getFirst();
        if(user != null) {
            Reservation reservation = new Reservation(user, flight);
            bookingApp.bookReservation(reservation);
        }
        


        MongoUtil.close();
    }
}