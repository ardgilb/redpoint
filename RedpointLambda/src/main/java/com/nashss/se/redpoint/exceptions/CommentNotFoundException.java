package com.nashss.se.redpoint.exceptions;

/**
 * Exception to throw when a given comment ID is not found in the database.
 */
public class CommentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 704235601212537166L;

    /**
     * Exception with no message or cause.
     */
    public CommentNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public CommentNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public CommentNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


