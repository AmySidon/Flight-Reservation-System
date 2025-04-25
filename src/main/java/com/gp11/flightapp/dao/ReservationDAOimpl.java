package com.gp11.flightapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.gp11.flightapp.model.Reservation;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

public class ReservationDAOimpl implements ReservationDAO {
    private final MongoCollection<Document> reservationCollection;

    public ReservationDAOimpl(MongoDatabase db) {
        this.reservationCollection = db.getCollection("Reservations");
    }
    @Override
    public void create(Reservation reservation) {
        Document doc = new Document("user_id", new ObjectId(reservation.getUserId()))
            .append("flight_id", new ObjectId(reservation.getFlightId()));
        reservationCollection.insertOne(doc);
    }
    @Override
    public Reservation read(String id) {
        Document doc = reservationCollection.find(eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        return new Reservation(
            doc.getObjectId("_id").toHexString(),
            doc.getObjectId("user_id").toHexString(),
            doc.getObjectId("flight_id").toHexString()
        );
    }
    @Override
    public List<Reservation> readByUserId(String id) {
        List<Reservation> reservations = new ArrayList<>();
        FindIterable<Document> docs = reservationCollection.find(eq("user_id", new ObjectId(id)));
        for (Document doc : docs) {
            reservations.add(new Reservation(
                doc.getObjectId("_id").toHexString(),
                doc.getObjectId("user_id").toHexString(),
                doc.getObjectId("flight_id").toHexString()
            ));
        }
        return reservations; 
    }
    @Override
    public List<Reservation> readByFlightId(String id) {
        List<Reservation> reservations = new ArrayList<>();
        FindIterable<Document> docs = reservationCollection.find(eq("flight_id", new ObjectId(id)));
        for (Document doc : docs) {
            reservations.add(new Reservation(
                doc.getObjectId("_id").toHexString(),
                doc.getObjectId("user_id").toHexString(),
                doc.getObjectId("flight_id").toHexString()
            ));
        }
        return reservations;
    }
    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        for (Document doc : reservationCollection.find()) {
            reservations.add(new Reservation(
                doc.getObjectId("_id").toHexString(),
                doc.getObjectId("user_id").toHexString(),
                doc.getObjectId("flight_id").toHexString()
            ));
        }
        return reservations;
    }
    @Override
    public void update(Reservation reservation) {
        Document updatedDoc = new Document("user_id", new ObjectId(reservation.getUserId()))
            .append("flight_id", new ObjectId(reservation.getFlightId()));
        reservationCollection.updateOne(eq("_id", new ObjectId(reservation.getId())), new Document("$set", updatedDoc));
    }
    @Override
    public void delete(String id) {
        reservationCollection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
