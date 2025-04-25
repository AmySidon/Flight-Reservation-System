// UserDAO.java : Converts between objects of User class and Documents in the database

package com.gp11.flightapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.gp11.flightapp.exceptions.DuplicateEmailException;
import com.gp11.flightapp.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

public class UserDAOimpl implements UserDAO {
    private final MongoCollection<Document> userCollection;

    public UserDAOimpl(MongoDatabase db) {
        this.userCollection = db.getCollection("Users");
    }
    @Override
    public void create(User user) throws DuplicateEmailException {
        String email = user.getEmail();
        if (readByEmail(email) != null) {
            throw new DuplicateEmailException(email);
        }
        Document doc = new Document("name", user.getName())
            .append("email", user.getEmail());
        userCollection.insertOne(doc);
    }
    @Override
    public User read(String id) {
        Document doc = userCollection.find(eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        return new User(
            doc.getObjectId("_id").toHexString(),
            doc.getString("name"),
            doc.getString("email")
        );
    }
    @Override
    public User readByEmail(String email) {
        Document doc = userCollection.find(eq("email", email)).first();
        if (doc == null) return null;
        return new User(doc.getObjectId("_id").toHexString(), doc.getString("name"), doc.getString("email"));
    }
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        for (Document doc : userCollection.find()) {
            users.add(new User(
                doc.getObjectId("_id").toHexString(),
                doc.getString("name"),
                doc.getString("email")
            ));
        }
        return users;
    }
    @Override
    public void update(User user) throws DuplicateEmailException {
        User existing = readByEmail(user.getEmail());
        if (existing != null && !existing.getId().equals(user.getId())) {
            throw new DuplicateEmailException(user.getEmail());
        }
        Document updatedDoc = new Document("name", user.getName())
            .append("email", user.getEmail());
        userCollection.updateOne(eq("_id", new ObjectId(user.getId())), new Document("$set", updatedDoc));
    }
    @Override
    public void delete(String id) {
        userCollection.deleteOne(eq("_id", new ObjectId(id)));
    }

    
}
