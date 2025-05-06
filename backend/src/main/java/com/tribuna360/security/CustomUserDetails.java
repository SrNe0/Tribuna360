package com.tribuna360.security;

import com.tribuna360.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    // Método para acceder al objeto Usuario
    private final Usuario usuario;  // Se pasa como parámetro al constructor

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getUsername() {
        return usuario.getCedula();  // Usamos la cédula como el username
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();  // Retorna la contraseña encriptada
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol())); // Mapeamos el rol
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Método estático para convertir a Usuario en CustomUserDetails
    public static UserDetails create(Usuario usuario) {
        return new CustomUserDetails(usuario);
    }
}
