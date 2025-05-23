package com.edutech.usuarios.controller;

import com.edutech.usuarios.controller.response.MessageResponse;
import com.edutech.usuarios.dto.UserDto;
import com.edutech.usuarios.repository.RoleRepository;
import com.edutech.usuarios.service.RoleService;
import com.edutech.usuarios.service.UserService;
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
    private UserService userService;
    @Autowired
    private RoleService roleService;

    //get para listar usuarios
        @GetMapping
        public ResponseEntity<List<User>> getUsers() {
            return ResponseEntity.ok(userService.getAllUsers());
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getUserById(@PathVariable Long id) {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }

    @PostMapping("/add")
    public ResponseEntity<MessageResponse> addUser(@Valid @RequestBody UserDto userDto) {
            userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse("Usuario agregado exitosamente."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDetails) {
        userService.updateUser(id, userDetails);
        return ResponseEntity.ok(new MessageResponse("Usuario actualizado exitosamente."));
    }


        @DeleteMapping("/{id}")
        public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
            userService.deleteUserById(id);
            return ResponseEntity.ok(new MessageResponse("Usuario eliminado correctamente."));
        }




}
