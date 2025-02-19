package com.reitansora.apilogin.controller;

import com.reitansora.apilogin.model.LoginRequest;
import com.reitansora.apilogin.model.LoginResponse;
import com.reitansora.apilogin.model.UserResponse;
import com.reitansora.apilogin.security.JWTIssuer;
import com.reitansora.apilogin.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JWTIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user and issues a JWT token.
     *
     * @param loginRequest the login request containing email and password
     * @return a LoginResponse containing the JWT token
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest){
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        var roles = principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        var token = jwtIssuer.issue(principal.getEncryptedUserId(), principal.getUsername(), principal.getEmail(), principal.getCreatedAt(), principal.getRole());
        return LoginResponse.builder()
            .accessToken(token)
            .build();
    }

    /**
     * Retrieves the authenticated user's details.
     *
     * @param userPrincipal the authenticated user's principal
     * @return a UserResponse containing the user's details
     */
    @GetMapping("/secured")
    public UserResponse secured(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return UserResponse.builder()
            .userId(userPrincipal.getEncryptedUserId())
            .username(userPrincipal.getUsername())
            .email(userPrincipal.getEmail())
            .password("")
            .createdAt(userPrincipal.getCreatedAt())
            .role(userPrincipal.getRole())
            .build();
    }
}
