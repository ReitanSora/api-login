package com.reitansora.apilogin.exception;

/**
 * Exception thrown when a user is not found by their ID.
 */
public class UserNotFound extends Exception {

    /**
     * Constructs a new UserNotFound exception with the specified user ID.
     *
     * @param id the ID of the user that was not found
     */
    public UserNotFound(String id) {
        super("User with id: " + id + " did not exist");
    }
}
