package com.reitansora.apilogin.exception;

/**
 * Exception thrown when an email is already in use by another account.
 */
public class EmailUsed extends Exception {

    /**
     * Constructs a new EmailUsed exception with the specified email.
     *
     * @param email the email that is already in use
     */
    public EmailUsed(String email) {
        super("The email '" + email + "' is in use by another account");
    }
}
