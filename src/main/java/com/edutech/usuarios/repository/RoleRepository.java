package com.edutech.usuarios.repository;


import com.edutech.usuarios.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
    Optional<Role> findByName(String admin);
    boolean existsByNameIgnoreCase(String name);
}