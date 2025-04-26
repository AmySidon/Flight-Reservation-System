package com.gp11.flightapp.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.gp11.flightapp.model.Flight;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;

public class FlightDAOimpl implements FlightDAO {
    private final MongoCollection<Document> flightCollection;

    public FlightDAOimpl(MongoDatabase db) {
        this.flightCollection = db.getCollection("Flights");
    }
    @Override
    public void create(Flight flight) {
        Date flightDate = Date.from(flight.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Document doc = new Document("destination", flight.getArrivalAirport())
            .append("origin", flight.getDepartureAirport())
            .append("date", flightDate);
        flightCollection.insertOne(doc);
    }

    @Override
    public Flight read(String id) {
        Document doc = flightCollection.find(eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        Date flightDate = doc.getDate("date");
        LocalDate localFlightDate = flightDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        return new Flight(
            doc.getObjectId("_id").toHexString(),
            doc.getString("destination"),
            doc.getString("origin"),
            localFlightDate
        );
    }
    
    @Override
    public List<Flight> readByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Flight> flights = new ArrayList<>();
        FindIterable<Document> docs = flightCollection.find(
            and(
                gte("date", Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())),
                lte("date", Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
            )
        );
        for (Document doc : docs) {
            flights.add(new Flight(
                doc.getObjectId("_id").toHexString(),
                doc.getString("origin"),
                doc.getString("destination"),
                doc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            ));
        }
        return flights;
    }
    @Override
    public List<Flight> readByOrigin(String origin) {
        List<Flight> flights = new ArrayList<>();
        FindIterable<Document> docs = flightCollection.find(eq("origin", origin));
        for (Document doc : docs) {
            flights.add(new Flight(
                doc.getObjectId("_id").toHexString(),
                doc.getString("origin"),
                doc.getString("destination"),
                doc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            ));
        }
        return flights;
    }
    @Override
    public List<Flight> readByDestination(String destination) {
        List<Flight> flights = new ArrayList<>();
        FindIterable<Document> docs = flightCollection.find(eq("destination", destination));
        for (Document doc : docs) {
            flights.add(new Flight(
                doc.getObjectId("_id").toHexString(),
                doc.getString("origin"),
                doc.getString("destination"),
                doc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            ));
        }
        return flights;
    }
    @Override
    public List<Flight> readByDateAndAirport(LocalDate startDate, LocalDate endDate, String origin, String destination){
        List<Flight> flights = new ArrayList<>();
        FindIterable<Document> docs = flightCollection.find(
            and(
                gte("date", Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())),
                lte("date", Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())),
                eq("destination", destination),
                eq("origin", origin)
            )
        );
        for (Document doc : docs) {
            flights.add(new Flight(
                doc.getObjectId("_id").toHexString(),
                doc.getString("origin"),
                doc.getString("destination"),
                doc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            ));
        }
        return flights;
    }
    @Override
    public List<Flight> getAll(){
        List<Flight> flights = new ArrayList<>();
        for (Document doc : flightCollection.find()) {
            Date flightDate = doc.getDate("origin");
            LocalDate localFlightDate = flightDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            flights.add(new Flight(
                doc.getObjectId("_id").toHexString(),
                doc.getString("origin"),
                doc.getString("destination"),
                localFlightDate
            ));
        }
        return flights;
    }

    @Override
    public void update(Flight flight) {
        Date flightDate = Date.from(flight.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Document updatedDoc = new Document("origin", flight.getDepartureAirport())
            .append("destination", flight.getArrivalAirport())
            .append("date", flightDate);
        flightCollection.updateOne(eq("_id", new ObjectId(flight.getId())), new Document("$set", updatedDoc));
    }

    @Override
    public void delete(String id) {
        flightCollection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
