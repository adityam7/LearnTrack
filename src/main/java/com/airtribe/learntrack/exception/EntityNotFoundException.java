package com.airtribe.learntrack.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityType, int id) {
        super(entityType + " with ID " + id + " not found");
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
