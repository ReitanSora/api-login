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

    @GetMapping(path = "/find")
    public ArrayList<UserEntity> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping(path = "/find/id/{id}")
    public Optional<UserEntity> getUserById(@PathVariable("id") long id){
        return this.userService.findById(id);
    }

    @GetMapping(path = "/find/email/{email}")
    public Optional<UserEntity> getUserByEmail(@PathVariable("email") String email){
        return this.userService.findByEmail(email);
    }

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
