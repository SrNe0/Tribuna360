package com.tribuna360.enums;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum UsuarioRolEnum {
    ADMIN,
    USER;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name())); // "ROLE_ADMIN", "ROLE_USER", etc.
    }
}

