package com.edutech.usuarios.controller;

import com.edutech.usuarios.controller.response.MessageResponse;
import com.edutech.usuarios.model.Role;
import com.edutech.usuarios.model.User;
import com.edutech.usuarios.repository.RoleRepository;
import com.edutech.usuarios.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/edutech/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Role> role = roleService.getRoleById(id);
        if (role.isPresent()) {
            return ResponseEntity.ok(role.get());
        } else {
            MessageResponse response = MessageResponse.builder()
                    .message("Role con ID " + id + " no encontrado.")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PostMapping
    public ResponseEntity<MessageResponse> addRole(@Valid @RequestBody Role role) {
        roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Rol creado exitosamente."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRole(@PathVariable Long id, @Valid @RequestBody Role roleDetails) {
        roleService.updateRole(id, roleDetails);
        return ResponseEntity.ok(new MessageResponse("Rol actualizado exitosamente."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRole(@PathVariable Long id) {
        if (!roleService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Rol no encontrado."));
        }
        roleService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Rol eliminado exitosamente."));
    }
}

