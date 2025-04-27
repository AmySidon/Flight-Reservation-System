package com.gp11.flightapp.model;

/**
 * Represens a user in the flight reservation system.
 * A user has a unique ID, a name, and an email address.
 */

public class User {
    private String id;
    private String name;
    private String email;
    /**
     * Default Constructor.
     * Initializes a usre with no attributes.
     */
    public User() {}
    /**
     * Constructor with strings as arguments.
     * @param id The unique identifier for the user.
     * @param name Name of the user.
     * @param email Email address of the user.
     */
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    /**
     * Constructor without ID argument. Sets id to null.
     * @param name Name of the user.
     * @param email User's email.
     */
    public User(String name, String email) {
        this.id = null;
        this.name = name;
        this.email = email;
    }
    /**
     * Gets the email address of the user.
     * @return User's email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Gets the ID of the user.
     * @return ID of user.
     */
    public String getId() {
        return id;
    }
    /**
     * Gets the name of user.
     * @return Name of user.
     */
    public String getName() {

        return name;
    }
    /**
     * Sets the email address of the user.
     * Business rule: email cannot be empty or blank.
     * @param email the email to be set.
     * @throws IllegalArgumentException if  email is empty or blank.
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty or blank.");
        }
        this.email = email; //business rule: cannot be empty string,
        //also maybe other checks for validity 
    }
    /**
     * Sets the User's ID.
     * @param id User's ID.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Sets the user's name.
     * @throws IllegalArgumentException if name is empty or blank.
     * @param name User's name.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or blank.");
        }
        this.name = name; //business rule: cannot be empty string
    }
    /**
     * Gives string of object.
     * @return string of object.
     */
    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email;
    }
    
    
}
