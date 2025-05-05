package com.edutech.usuarios.controller;

import com.edutech.usuarios.controller.response.MessageResponse;
import com.edutech.usuarios.repository.RoleRepository;
import com.edutech.usuarios.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edutech.usuarios.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edutech.usuarios.model.User;
import com.edutech.usuarios.model.Role;

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
    @Autowired
    private RoleRepository roleRepository;

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
                if (userRepository.existsByEmail(user.getEmail())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new MessageResponse("Ya existe un usuario con ese correo."));
                }
                if (!roleRepository.existsById(user.getRole().getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new MessageResponse("El rol no existe."));
                }

                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new MessageResponse("Usuario agregado exitosamente."));
            } catch (ConstraintViolationException e) {
                // Extrae solo los mensajes de violación
                String mensaje = e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .findFirst()
                        .orElse("Error de validación");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Error al crear el usuario: '" + mensaje + "'"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Error inesperado al crear el usuario: " + e.getMessage()));
            }

        }
}
