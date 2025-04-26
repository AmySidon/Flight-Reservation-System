package com.gp11.flightapp;

import java.time.LocalDate;
import java.util.List;

import com.gp11.flightapp.dao.BookingService;
import com.gp11.flightapp.model.Flight;
import com.gp11.flightapp.utils.MongoUtil;

public class Main {
    public static void main(String[] args) {
        BookingService bookingApp = new BookingService(MongoUtil.getDatabase());
        //User user = bookingApp.searchUserEmail("spac3b1k3r@blackmesa.com");
        LocalDate time1 = LocalDate.of(2025, 4, 15);
        LocalDate time2 = LocalDate.of(2025, 4, 24);
        String airport1 = "LAX";
        String airport2 = "SFO";
        List<Flight> flights = bookingApp.searchFlights(time1, time2, airport1, airport2);
        for (Flight flight : flights) {
            if (flight != null) {
                System.out.println(flight.toString());
            }
        }

        MongoUtil.close();
    }
}