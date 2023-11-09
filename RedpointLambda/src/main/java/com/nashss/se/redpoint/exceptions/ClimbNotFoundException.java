package com.nashss.se.redpoint.exceptions;

/**
 * Exception to throw when a given Climb ID is not found in the database.
 */
public class ClimbNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7671685487568631891L;

    /**
     * Exception with no message or cause.
     */
    public ClimbNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ClimbNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ClimbNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ClimbNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


