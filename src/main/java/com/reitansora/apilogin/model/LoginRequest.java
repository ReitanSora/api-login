package com.reitansora.apilogin.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a login request containing email and password.
 */
@Getter
@Builder
public class LoginRequest {

    /**
     * The email of the user attempting to log in.
     */
    public String email;

    /**
     * The password of the user attempting to log in.
     */
    public String password;

}
