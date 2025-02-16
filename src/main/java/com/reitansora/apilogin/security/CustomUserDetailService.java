package com.reitansora.apilogin.security;

import com.reitansora.apilogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Custom implementation of UserDetailsService to load user-specific data.
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    /**
     * Loads the user details by username (email).
     *
     * @param username the email of the user to load
     * @return UserDetails containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByEmail(username).orElseThrow();
        return UserPrincipal.builder()
            .userId(user.getId())
            .encryptedUserId(user.getUser_id())
            .username(user.getUser_nickname())
            .email(user.getUser_email())
            .password(user.getUser_password())
            .createdAt(user.getCreated_at().toString())
            .role(user.getUser_role())
            .build();
    }
}
