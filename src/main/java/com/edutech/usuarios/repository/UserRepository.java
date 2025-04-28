package com.edutech.usuarios.repository;

import com.edutech.usuarios.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
