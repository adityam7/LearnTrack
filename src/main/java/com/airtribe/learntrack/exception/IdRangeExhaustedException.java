package com.airtribe.learntrack.exception;

/**
 * Thrown when an ID range has been completely exhausted
 */
public class IdRangeExhaustedException extends RuntimeException {

    public IdRangeExhaustedException(String message) {
        super(message);
    }

    public IdRangeExhaustedException(String message, Throwable cause) {
        super(message, cause);
    }
}
