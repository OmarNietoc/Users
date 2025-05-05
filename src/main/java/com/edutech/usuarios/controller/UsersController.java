package com.edutech.usuarios.controller;

import com.edutech.usuarios.controller.response.MessageResponse;
import com.edutech.usuarios.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edutech.usuarios.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edutech.usuarios.model.User;

@RestController
@RequestMapping("/users")
public class UsersController {

    //private final UserService userService;

    // Inyección de dependencia a través del constructor
    /*public UsersController(UserService userService) {
        this.userService = userService;
    }*/


    @Autowired
    private UserRepository userRepository;

    //get para listar usuarios
        @GetMapping
        public ResponseEntity<List<User>> getUsers() {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        }
        @GetMapping("/{id}")
        public ResponseEntity<?> getUserById(@PathVariable Long id) {
            Optional <User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                MessageResponse response = MessageResponse.builder()
                        .message("Usuario con ID " + id + " no encontrado.")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }

        @PostMapping("/add")
    public ResponseEntity<MessageResponse> addUser(@RequestBody User user) {

            try {
                // Asegurarse de que el ID venga nulo (o ignorar si viene)
                user.setId(null);

                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new MessageResponse("Usuario agregado exitosamente."));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Error al crear el usuario: " + e.getMessage()));
            }

        }
}
