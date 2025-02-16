package com.reitansora.apilogin.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Component responsible for decoding JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final JwtProperties jwtProperties;

    /**
     * Decodes the given JWT token using the secret key from JwtProperties.
     *
     * @param token the JWT token to decode
     * @return the decoded JWT
     */
    public DecodedJWT decode (String token){
        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
            .build()
            .verify(token);
    }
}
