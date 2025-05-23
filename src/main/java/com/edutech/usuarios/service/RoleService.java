package com.edutech.usuarios.service;

import com.edutech.usuarios.model.Role;
import com.edutech.usuarios.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return roleRepository.existsById(id);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    public void updateRole(Long id, Role roleDetails) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol con ID " + id + " no encontrado."));

        existingRole.setName(roleDetails.getName());
        roleRepository.save(existingRole);
    }

}
