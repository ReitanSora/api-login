package com.reitansora.apilogin.service;

import com.reitansora.apilogin.entity.UserEntity;
import com.reitansora.apilogin.exception.EmailUsed;
import com.reitansora.apilogin.exception.UserNotFound;
import com.reitansora.apilogin.repository.UserRepository;
import com.reitansora.apilogin.util.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    UserValidation userValidation;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserService with the given UserRepository.
     *
     * @param userRepository the user repository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userValidation = new UserValidation();
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return a list of all users
     */
    public ArrayList<UserEntity> getAllUsers() {
        return (ArrayList<UserEntity>) userRepository.findAll();
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email to search for
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Checks if a user exists by their email.
     *
     * @param email the email to search for
     * @return true if the user exists, false otherwise
     */
    public boolean findByEmailPublic(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID to search for
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @throws EmailUsed if the email is already used by another user
     */
    public void createUser(UserEntity user) throws EmailUsed{
        if(userRepository.findByEmail(user.getUser_email()).isEmpty()){
            user.setUser_password(encryptPassword(user.getUser_password()));
            userRepository.save(user);
        }
        else{
            throw new EmailUsed(user.getUser_email());
        }
    }

    /**
     * Updates an existing user.
     *
     * @param userRequest the user data to update
     * @param request_id the encrypted user ID
     * @throws EmailUsed if the email is already used by another user
     * @throws UserNotFound if the user is not found
     */
    public void updateUser(UserEntity userRequest, String request_id) throws EmailUsed, UserNotFound {
        // Find the real ID of the user from the encrypted user_id
        Optional<Long> optionalId = userRepository.findBy_id(request_id);
        if (optionalId.isEmpty()) {
            throw new UserNotFound(request_id);
        }

        // Get the real ID of the user
        Long userId = optionalId.get();

        // Find the user by the real ID
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFound(request_id);
        }

        // Get the existing user
        UserEntity userEntity = optionalUser.get();

        // Check if the email is being used by another user
        if (userRequest.getUser_email() != null && !userRequest.getUser_email().equals(userEntity.getUser_email())) {
            Optional<UserEntity> existingUserWithEmail = userRepository.findByEmail(userRequest.getUser_email());
            if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(userId)) {
                throw new EmailUsed(userRequest.getUser_email());
            }
        }

        // Update only the fields provided in the request
        if (userRequest.getUser_nickname() != null) {
            userEntity.setUser_nickname(userRequest.getUser_nickname());
        }

        if (userRequest.getUser_email() != null) {
            userEntity.setUser_email(userRequest.getUser_email());
        }

        if (userRequest.getUser_password() != null) {
            userEntity.setUser_password(encryptPassword(userRequest.getUser_password()));
        }

        if (userRequest.getUser_role() != null) {
            userEntity.setUser_role(userRequest.getUser_role());
        }

        // Save the changes to the database
        userRepository.saveAndFlush(userEntity);
    }

    /**
     * Deletes a user by their encrypted ID.
     *
     * @param id the encrypted user ID
     * @throws UserNotFound if the user is not found
     */
    public void deleteUser(String id) throws UserNotFound {
        if (userRepository.findById(userRepository.findBy_id(id).get()).isPresent()){
            userRepository.deleteById(userRepository.findBy_id(id).get());
        }else{
            throw new UserNotFound(id);
        }
    }

    /**
     * Encrypts a password using the password encoder.
     *
     * @param password the password to encrypt
     * @return the encrypted password
     */
    public String encryptPassword(String password) {
        return this.passwordEncoder.encode(password);
    }
}
