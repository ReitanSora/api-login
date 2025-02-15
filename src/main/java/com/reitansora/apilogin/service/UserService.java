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

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    UserValidation userValidation;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userValidation = new UserValidation();
    }

    public ArrayList<UserEntity> getAllUsers() {
        return (ArrayList<UserEntity>) userRepository.findAll();
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean findByEmailPublic(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public void createUser(UserEntity user) throws EmailUsed{
        if(userRepository.findByEmail(user.getUser_email()).isEmpty()){
            user.setUser_password(encryptPassword(user.getUser_password()));
            userRepository.save(user);
        }
        else{
            throw new EmailUsed(user.getUser_email());
        }
    }

    public void updateUser(UserEntity userRequest, String request_id) throws EmailUsed, UserNotFound {
        // Buscar el ID real del usuario a partir del user_id encriptado
        Optional<Long> optionalId = userRepository.findBy_id(request_id);
        if (optionalId.isEmpty()) {
            throw new UserNotFound(request_id);
        }

        // Obtener el ID real del usuario
        Long userId = optionalId.get();

        // Buscar el usuario por el ID real
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFound(request_id);
        }

        // Obtener el usuario existente
        UserEntity userEntity = optionalUser.get();

        // Verificar si el email está siendo usado por otro usuario
        if (userRequest.getUser_email() != null && !userRequest.getUser_email().equals(userEntity.getUser_email())) {
            Optional<UserEntity> existingUserWithEmail = userRepository.findByEmail(userRequest.getUser_email());
            if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(userId)) {
                throw new EmailUsed(userRequest.getUser_email());
            }
        }

        // Actualizar solo los campos proporcionados en la solicitud
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

        // Guardar los cambios en la base de datos
        userRepository.saveAndFlush(userEntity);
    }

    public void deleteUser(String id) throws UserNotFound {
        if (userRepository.findById(userRepository.findBy_id(id).get()).isPresent()){
            userRepository.deleteById(userRepository.findBy_id(id).get());
        }else{
            throw new UserNotFound(id);
        }
    }

    public String encryptPassword(String password) {
        return this.passwordEncoder.encode(password);
    }
}
