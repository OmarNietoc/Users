package com.edutech.usuarios.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "users")
@Data  // Lombok genera getters, setters, equals, hashCode y toString automáticamente
@NoArgsConstructor  // Constructor vacío por defecto (necesario para JPA)
@AllArgsConstructor  // Constructor con todos los parámetros, útil si lo necesitas para la creación rápida de instancias
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Email(message = "El correo debe ser válido")
    @NotEmpty(message = "El correo no puede estar vacío")
    private String email;

    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;


    private Integer role;

    private Integer status;

    public User(String name, String email, String password,Integer role, Integer status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}
