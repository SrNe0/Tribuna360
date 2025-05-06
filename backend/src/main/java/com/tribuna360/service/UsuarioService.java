package com.tribuna360.service;

import com.tribuna360.model.Usuario;
import com.tribuna360.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);  // Guardamos el usuario en la base de datos
    }


    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);  // Buscamos un usuario por su ID
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);  // Eliminamos un usuario por su ID
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);  // Buscamos un usuario por su email
    }

    public Optional<Usuario> obtenerUsuarioPorCedula(String cedula) {
        return usuarioRepository.findByCedula(cedula);  // Buscamos un usuario por su cédula
    }
}
