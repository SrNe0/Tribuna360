package com.tribuna360.model;

import com.tribuna360.enums.UsuarioRolEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity // Indica que esta clase es una entidad de base de datos
@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
@Table(name = "usuario") // Mapea esta entidad a la tabla "usuario" en la base de datos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;  // Este campo no debe incluirse en el Builder

    @Column(nullable = false, unique = true)
    private String cedula;

    private String nombre;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;

    private String contrasena;

    private LocalDate fechaRegistro;

    // Relación con el rol: ADMIN o USER
    @Enumerated(EnumType.STRING)  // Almacenamos el valor como STRING (ADMIN, USUARIO)
    private UsuarioRolEnum rol;

    // El campo idUsuario no debe ser incluido en el Builder porque JPA lo autogenera
    // Lombok generará todos los getters y setters automáticamente
}
