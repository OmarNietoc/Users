package com.edutech.usuarios.service;

import com.edutech.usuarios.exception.ResourceNotFoundException;
import com.edutech.usuarios.model.Role;

import com.edutech.usuarios.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        Optional <Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isEmpty()) {
            throw new ResourceNotFoundException("Role no encontrado"  );
        }
        Role roleFound = roleOpt.get();
        return roleFound;
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
        Role existingRole = getRoleById(id);
        existingRole.setName(roleDetails.getName());
        roleRepository.save(existingRole);
    }

}
