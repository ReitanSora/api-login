package com.reitansora.apilogin.controller;


import com.reitansora.apilogin.entity.UserEntity;
import com.reitansora.apilogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @GetMapping(path = "/find")
    public ArrayList<UserEntity> getAllUsers(){
        return this.userService.getAllUsers();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    @GetMapping(path = "/find/id/{id}")
    public Optional<UserEntity> getUserById(@PathVariable("id") long id){
        return this.userService.findById(id);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    @GetMapping(path = "/find/email/{email}")
    public Optional<UserEntity> getUserByEmail(@PathVariable("email") String email){
        return this.userService.findByEmail(email);
    }

    /**
     * Checks if an email is already used.
     *
     * @param email the email to check
     * @return a ResponseEntity containing a map with the email and a boolean indicating if it is used
     */
    @GetMapping(path = "/isEmailUsed")
    public ResponseEntity<Map<String, Object>> isEmailUsed(@RequestParam(name = "email") String email){
        Map<String, Object> response = new HashMap<>();

        try {
            boolean isUsed = userService.findByEmailPublic(email); // Verifica si el correo está en uso
            response.put("email", email);
            response.put("isUsed", isUsed);

            return ResponseEntity.ok(response); // Devuelve un JSON con la información
        } catch (Exception e) {
            response.put("error", "Ocurrió un error al verificar el correo");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return true if the user was created successfully, false otherwise
     */
    @PostMapping(path = "/create")
    public boolean createUser(@RequestBody UserEntity user){
        try {
            this.userService.createUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing user.
     *
     * @param user the user to update
     * @param id the ID of the user to update
     * @return true if the user was updated successfully, false otherwise
     */
    @PutMapping(path = "/edit/{id}")
    public boolean updateUser(@RequestBody UserEntity user, @PathVariable("id") String id){
        try {
            this.userService.updateUser(user,id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user.
     *
     * @param id the ID of the user to delete
     * @return true if the user was deleted successfully, false otherwise
     */
    @DeleteMapping(path = "/delete/{id}")
    public boolean deleteUser(@PathVariable("id") String id){
        try {
            this.userService.deleteUser(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
