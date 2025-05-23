package com.edutech.usuarios.service;


import com.edutech.usuarios.dto.UserDto;
import com.edutech.usuarios.exception.ResourceNotFoundException;
import com.edutech.usuarios.model.User;
import com.edutech.usuarios.model.Role;

import com.edutech.usuarios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional <User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new ResourceNotFoundException("Usuario no encontrado"  );
        }
        User userFound = userOpt.get();
        return userFound;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByEmailAndIdNot(String email, Long id) {
        return userRepository.existsByEmailAndIdNot(email, id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        User user = getUserById(id);
        userRepository.deleteById(id);
    }

    private Role emailAndRoleValidator(String email, Long roleId, Long userIdToExclude) {
        // Validación de correo
        if (userIdToExclude == null) {
            if (existsByEmail(email)) {
                throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
            }
        } else {
            if (existsByEmailAndIdNot(email, userIdToExclude)) {
                throw new IllegalArgumentException("Ya existe otro usuario con ese correo.");
            }
        }

        // Validación de rol
        return roleService.getRoleById(roleId);
    }


    public void createUser(UserDto userDto) {

        Role role = emailAndRoleValidator(userDto.getEmail(), userDto.getRole(), null);

        User user = new User(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                role,
                userDto.getStatus()
        );

        userRepository.save(user);
    }

    public void updateUser(Long id, UserDto userDetails) {

        User user= getUserById(id);

        Role role = emailAndRoleValidator(userDetails.getEmail(), userDetails.getRole(), id);

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setStatus(userDetails.getStatus());
        user.setRole(role);

        userRepository.save(user);
    }

}
