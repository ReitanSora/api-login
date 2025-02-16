package com.reitansora.apilogin.exception;

/**
 * Exception thrown when validation of email or password fails.
 */
public class ValidationFailed extends Exception {

    /**
     * Constructs a new ValidationFailed exception with a default message.
     */
    public ValidationFailed() {
        super("Validation failed, email or password is incorrect");
    }
}
