package com.nashss.se.redpoint.exceptions;

/**
 * Exception to throw when a given area ID is not found in the database.
 */
public class LogbookEntryNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -2966076060688523994L;

    /**
     * Exception with no message or cause.
     */
    public LogbookEntryNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public LogbookEntryNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public LogbookEntryNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public LogbookEntryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

