package com.gp11.flightapp.exceptions;

public class DeletionRestrictedException extends DAOException {
    public DeletionRestrictedException(String entityType, String id) {
        super("Cannot delete " + entityType + " with ID " + id + " because it is associated with existing reservations.");
    }
}
