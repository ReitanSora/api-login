package com.reitansora.apilogin.repository;

import com.reitansora.apilogin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user entity by email using a native query.
     *
     * @param email the email of the user to find
     * @return an Optional containing the found UserEntity, or empty if not found
     */
    @Query(
        value = "SELECT * FROM raidendrive.select_by_email(request_email := :email) ",
        nativeQuery = true
    )
    Optional<UserEntity> findByEmail(@Param("email") String email);

    /**
     * Finds a user ID by user ID using a native query.
     *
     * @param id the ID of the user to find
     * @return an Optional containing the found user ID, or empty if not found
     */
    @Query(
        value = "SELECT * FROM raidendrive.select_by_id(request_id := :id) ",
        nativeQuery = true
    )
    Optional<Long> findBy_id(@Param("id") String id);

}
