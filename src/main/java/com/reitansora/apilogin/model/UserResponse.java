package com.reitansora.apilogin.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a response containing user details.
 */
@Getter
@Builder
public class UserResponse {

    /**
     * The unique identifier of the user.
     */
    private final Long id;

    /**
     * The user ID of the user.
     */
    private final String userId;

    /**
     * The username of the user.
     */
    private final String username;

    /**
     * The email of the user.
     */
    private final String email;

    /**
     * The password of the user.
     */
    private final String password;

    /**
     * The creation timestamp of the user account.
     */
    private final String createdAt;

    /**
     * The role of the user.
     */
    private final String role;
}
