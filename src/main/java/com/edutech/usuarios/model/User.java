package com.edutech.usuarios.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data  // Lombok genera getters, setters, equals, hashCode y toString automáticamente
@NoArgsConstructor  // Constructor vacío por defecto (necesario para JPA)
@AllArgsConstructor  // Constructor con todos los parámetros, útil si lo necesitas para la creación rápida de instancias
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "'name' no puede estar vacío")
    @Size(min = 4, max = 100, message = "'name' debe tener entre 4 y 100 caracteres")
    private String name;

    @Email(message = "'email' debe ser válido")
    @NotEmpty(message = "'email' no puede estar vacío")
    private String email;

    @NotEmpty(message = "'password' no puede estar vacía")
    @Size(min = 6, message = "'password' debe tener al menos 6 caracteres")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER para que cargue el rol junto con el usuario
    @JoinColumn(name = "id_role", nullable = false)
    @NotNull(message = "'role' no puede ser nulo")
    private Role role;

    /*
    @NotNull(message = "'role' no puede ser nulo")
    @Min(value = 0, message = "'role' debe ser mayor o igual a 0")
    private Integer role;
    */



    @NotNull(message = "'status' no puede ser nulo")
    @Min(value = 0, message = "El 'status' debe ser 1 o 0")
    @Max(value = 1, message = "El 'status' debe ser 1 o 0")
    private Integer status;

    public User(String name, String email, String password,Role role, Integer status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}
