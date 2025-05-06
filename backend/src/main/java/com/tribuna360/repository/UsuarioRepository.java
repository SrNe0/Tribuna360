package com.tribuna360.repository;

import com.tribuna360.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar un usuario por su email
    Usuario findByEmail(String email);

    // Método para buscar un usuario por su cédula
    Optional<Usuario> findByCedula(String cedula);

    // Método para verificar si un usuario existe por cédula
    boolean existsByCedula(String cedula);

    // Método para verificar si un usuario existe por email
    boolean existsByEmail(String email);

    // Método para verificar si un usuario existe por teléfono
    boolean existsByTelefono(String telefono);
}
