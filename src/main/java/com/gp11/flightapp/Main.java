package com.gp11.flightapp;

import com.gp11.flightapp.dao.FlightDAOimpl;
import com.gp11.flightapp.dao.ReservationDAOimpl;
import com.gp11.flightapp.dao.UserDAOimpl;
import com.gp11.flightapp.model.Flight;
import com.gp11.flightapp.model.Reservation;
import com.gp11.flightapp.model.User;
import com.gp11.flightapp.utils.MongoUtil;

public class Main {
    public static void main(String[] args) {
        UserDAOimpl userDAO = new UserDAOimpl(MongoUtil.getDatabase());
        FlightDAOimpl flightDAO = new FlightDAOimpl(MongoUtil.getDatabase());

        ReservationDAOimpl reservationDAO = new ReservationDAOimpl(MongoUtil.getDatabase());
        User myUser = userDAO.read("6809bf75e4ee8ca441021bac");

        System.out.println(myUser.toString());
        Reservation myReservation = reservationDAO.readByUserId(myUser.getId()).getFirst();
        
        Flight myFlight = flightDAO.read(myReservation.getFlightId());
        System.out.println(myFlight.toString());
        MongoUtil.close();
    }
}