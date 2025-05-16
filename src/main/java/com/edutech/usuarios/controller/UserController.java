package com.edutech.usuarios.controller;

import com.edutech.usuarios.controller.response.MessageResponse;
import com.edutech.usuarios.dto.UserDto;
import com.edutech.usuarios.repository.RoleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edutech.usuarios.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import com.edutech.usuarios.model.User;
import com.edutech.usuarios.model.Role;

@RestController
@RequestMapping("/edutech/users")
public class UserController {

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

    @PostMapping
    public ResponseEntity<MessageResponse> addUser(@Valid @RequestBody UserDto userDto) {

        try {
            // Asegurarse de que el ID venga nulo (o ignorar si viene)
            if (userRepository.existsByEmail(userDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Ya existe un usuario con ese correo."));
            }
            Optional<Role> roleOptional = roleRepository.findById(userDto.getRole());
            if (roleOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("El rol no existe."));
            }
            User user = new User(
                    userDto.getName(),
                    userDto.getEmail(),
                    userDto.getPassword(),
                    roleOptional.get(),
                    userDto.getStatus()
            );
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
        @PutMapping("/{id}")
        public ResponseEntity<MessageResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDetails) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse("Usuario con ID " + id + " no encontrado."));
            }

            if (userRepository.existsByEmailAndIdNot(userDetails.getEmail(), id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("Ya existe otro usuario con ese correo."));
            }




            // Validar que el rol exista
            Optional<Role> roleOptional = roleRepository.findById(userDetails.getRole());
            if (roleOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("El rol no existe."));
            }

            User user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setStatus(userDetails.getStatus());
            user.setRole(roleOptional.get());

            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Usuario actualizado exitosamente."));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse("Usuario con ID " + id + " no encontrado."));
            }

            userRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Usuario eliminado correctamente."));
        }




}
