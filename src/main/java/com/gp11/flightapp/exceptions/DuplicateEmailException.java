package com.gp11.flightapp.exceptions;

public class DuplicateEmailException extends DAOException {
    public DuplicateEmailException(String email) {
        super("A user with email " + email + " already exists.");
    }
}
