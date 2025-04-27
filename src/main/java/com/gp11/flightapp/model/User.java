package com.gp11.flightapp.model;


public class User {
    private String id;
    private String name;
    private String email;

    public User() {}

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email) {
        this.id = null;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or blank.");
        }
        return name;
    }
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty or blank.");
        }
        this.email = email; //business rule: cannot be empty string,
        //also maybe other checks for validity 
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name; //business rule: cannot be empty string
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email;
    }
    
    
}
