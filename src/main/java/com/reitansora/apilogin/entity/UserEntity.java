package com.reitansora.apilogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a user entity in the database.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user ID.
     */
    @Column
    private String user_id;

    /**
     * The user's nickname.
     */
    @Column
    private String user_nickname;

    /**
     * The user's email.
     */
    @Column
    private String user_email;

    /**
     * The user's password.
     */
    @Column
    private String user_password;

    /**
     * The timestamp when the user was created.
     */
    @Column(updatable = false, nullable = false)
    private Instant created_at;

    /**
     * The user's role.
     */
    @Column
    private String user_role;

    /**
     * Sets the creation timestamp and default user role before persisting.
     */
    @PrePersist
    protected void onCreate() {
        created_at = Instant.now();
        user_role = "client";
    }

}
