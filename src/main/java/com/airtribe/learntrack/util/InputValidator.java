package com.airtribe.learntrack.util;

public class InputValidator {

    /**
     * Validates that a string is not null and not empty
     * @param value the string to validate
     * @param fieldName the name of the field being validated (for error messages)
     * @throws IllegalArgumentException if the string is null or empty
     */
    public static void validateNotNullOrEmpty(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    /**
     * Validates that an email has a basic valid format
     * @param email the email to validate
     * @throws IllegalArgumentException if the email is null, empty, or invalid
     */
    public static void validateEmail(String email) {
        validateNotNullOrEmpty(email, "Email");

        // Basic email validation: contains @ and . after @
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email must contain '@' symbol");
        }

        String[] parts = email.split("@");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        if (parts[0].trim().isEmpty()) {
            throw new IllegalArgumentException("Email must have a local part before '@'");
        }

        if (parts[1].trim().isEmpty() || !parts[1].contains(".")) {
            throw new IllegalArgumentException("Email must have a valid domain after '@'");
        }
    }

    /**
     * Validates that an ID is not null and is positive
     * @param id the ID to validate
     * @param fieldName the name of the field being validated (for error messages)
     * @throws IllegalArgumentException if the ID is null or not positive
     */
    public static void validateId(Long id, String fieldName) {
        if (id == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive number");
        }
    }
}
