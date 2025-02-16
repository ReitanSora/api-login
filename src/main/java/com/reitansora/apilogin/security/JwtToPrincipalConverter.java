package com.reitansora.apilogin.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component responsible for converting a decoded JWT to a UserPrincipal.
 */
@Component
public class JwtToPrincipalConverter {

    /**
     * Converts a decoded JWT to a UserPrincipal.
     *
     * @param decodedJWT the decoded JWT
     * @return the UserPrincipal containing user information
     */
    public UserPrincipal convert(DecodedJWT decodedJWT) {
        return UserPrincipal.builder()
            .encryptedUserId(String.valueOf(decodedJWT.getSubject()))
            .username(decodedJWT.getClaim("nickname").asString())
            .email(decodedJWT.getClaim("email").asString())
            .createdAt(decodedJWT.getClaim("createdAt").asString())
            .role(decodedJWT.getClaim("role").asString())
            .build();
    }

    /**
     * Extracts authorities from the decoded JWT.
     *
     * @param decodedJWT the decoded JWT
     * @return a list of SimpleGrantedAuthority
     */
    private List<SimpleGrantedAuthority> getAuthorities(DecodedJWT decodedJWT) {
        var claim = decodedJWT.getClaim("authorities");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
