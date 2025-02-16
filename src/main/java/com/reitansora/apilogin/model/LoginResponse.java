package com.reitansora.apilogin.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a response to a login request containing an access token.
 */
@Getter
@Builder
public class LoginResponse {

    /**
     * The access token provided upon successful login.
     */
    private final String accessToken;

}
