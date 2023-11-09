package com.nashss.se.redpoint.exceptions;

/**
 * Exception to throw when a given area ID is not found in the database.
 */
public class AreaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4271653468876978430L;

    /**
     * Exception with no message or cause.
     */
    public AreaNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public AreaNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public AreaNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public AreaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

