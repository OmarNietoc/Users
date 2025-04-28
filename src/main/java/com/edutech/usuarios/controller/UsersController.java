package com.edutech.usuarios.controller;

import com.edutech.usuarios.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import com.edutech.usuarios.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import com.edutech.usuarios.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    // Inyección de dependencia a través del constructor
    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    private UserRepository userRepository;

    // Usar @PostConstruct para inicializar los usuarios después de que la aplicación esté lista
   /* @PostConstruct
    public void init() {
        // Crear usuarios sin id (el id se generará automáticamente)
        User user1 = new User("John Doe", "user1@gmail.com", "password1");
        User user2 = new User("Jona Hill", "user2@gmail.com", "password2");

        // Guardar los usuarios en la base de datos
        userRepository.save(user1);
        userRepository.save(user2);
    }*/


    //get para listar usuarios
        @GetMapping
        public ResponseEntity<List<User>> getUsers() {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }


}
