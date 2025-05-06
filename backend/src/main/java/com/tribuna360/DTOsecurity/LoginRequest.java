package com.tribuna360.DTOsecurity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok genera automáticamente los métodos getters, setters, equals, hashCode y toString.
@Builder // Lombok proporciona un patrón de diseño builder para crear instancias de esta clase de forma fluida.
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros.
@NoArgsConstructor // Lombok genera un constructor sin parámetros.
public class LoginRequest {
    private String cedula;
    private String password;
}
