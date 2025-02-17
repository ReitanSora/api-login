package com.reitansora.apilogin.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Custom authentication token that holds a UserPrincipal.
 */
public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    /**
     * Constructs a UserPrincipalAuthenticationToken with the given UserPrincipal.
     *
     * @param principal the authenticated user principal
     */
    public UserPrincipalAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    /**
     * Returns the credentials of the authenticated user.
     *
     * @return null as credentials are not used in this token
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Returns the authenticated user principal.
     *
     * @return the UserPrincipal
     */
    @Override
    public UserPrincipal getPrincipal() {
        return principal;
    }
}
