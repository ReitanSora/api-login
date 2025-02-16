package com.reitansora.apilogin.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Component responsible for issuing JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JWTIssuer {

    private final JwtProperties properties;

    /**
     * Issues a JWT token with the given user details.
     *
     * @param encryptedUserId the encrypted user ID
     * @param nickname the nickname of the user
     * @param email the email of the user
     * @param createdAt the creation timestamp of the user account
     * @param role the role of the user
     * @return the issued JWT token
     */
    public String issue(String encryptedUserId, String nickname,String email, String createdAt, String role) {
        return JWT.create()
            .withSubject(String.valueOf(encryptedUserId))
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plus(Duration.of(7, ChronoUnit.DAYS)))
            .withClaim("nickname", nickname)
            .withClaim("email", email)
            .withClaim("createdAt", createdAt)
            .withClaim("role", role)
            .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}
