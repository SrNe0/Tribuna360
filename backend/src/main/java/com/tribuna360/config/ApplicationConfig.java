package com.tribuna360.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tribuna360.repository.UsuarioRepository;
import com.tribuna360.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/* Esta es una clase de configuración que gestiona los aspectos relacionados con la seguridad,
 la autenticación de usuarios, la codificación de contraseñas y la serialización/deserialización de objetos.
 La anotación @Configuration indica que esta clase contiene beans de configuración para Spring. */
@Configuration
@RequiredArgsConstructor // Lombok genera automáticamente un constructor con los parámetros finales necesarios
public class ApplicationConfig {

    // Inyección de dependencia del repositorio de usuarios para acceder a la base de datos
    private final UsuarioRepository usuarioRepository;

    /* Este bean configura el AuthenticationManager, que es responsable de manejar la autenticación de los usuarios. */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();  // Obtiene el AuthenticationManager de la configuración
    }

    /* Este bean configura un AuthenticationProvider que usa DaoAuthenticationProvider para la autenticación con base de datos. */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        // Se crea una instancia de DaoAuthenticationProvider, que se utiliza para la autenticación con base de datos
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService());  // Establece el servicio que carga los detalles del usuario
        authenticationProvider.setPasswordEncoder(passwordEncoder());  // Establece el codificador de contraseñas
        return authenticationProvider;  // Devuelve el AuthenticationProvider configurado
    }

    /* Este bean configura el UserDetailsService que se usa para cargar los detalles del usuario durante el proceso de autenticación. */
    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(usuarioRepository);  // Utiliza CustomUserDetailsService para la autenticación
    }

    /* Este bean configura el PasswordEncoder, que se utiliza para codificar y verificar las contraseñas. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Devuelve un codificador BCrypt
    }

    /* Este bean configura el ObjectMapper que se utiliza para convertir objetos Java a JSON y viceversa. */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();  // Crea una nueva instancia de ObjectMapper
        mapper.registerModule(new JavaTimeModule());  // Registra el módulo JavaTime para manejar fechas
        return mapper;  // Devuelve el ObjectMapper configurado
    }
}
