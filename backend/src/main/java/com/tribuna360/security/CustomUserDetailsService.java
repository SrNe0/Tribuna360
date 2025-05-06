package com.tribuna360.security;

import com.tribuna360.repository.UsuarioRepository;
import com.tribuna360.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cedula) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByCedula(cedula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convertimos el Usuario a CustomUserDetails y lo devolvemos
        return CustomUserDetails.create(usuario);  // Devuelve un objeto CustomUserDetails
    }
}
