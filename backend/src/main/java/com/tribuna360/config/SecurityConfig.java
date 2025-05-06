package com.tribuna360.config;

import com.tribuna360.security.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter JWTAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(c -> c.configurationSource(corsConfigurationSource()))  // Configurar CORS
                .csrf(AbstractHttpConfigurer::disable)  // Desactivar CSRF
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                // Rutas sin autenticación (Permitir acceso a las rutas de autenticación sin token JWT)
                                .requestMatchers("/auth/**",
                                        "/swagger.html",
                                        "/swagger-ui/*",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/docs/",
                                        "/v3/api-docs/*",
                                        "/v3/api-docs/**",
                                        "/v3/api-docs.yaml",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/webjars/**").permitAll()

// Permitir acceso a /api/abonos/comprar a usuarios con rol USER o ADMIN
                                .requestMatchers("/api/abonos/comprar").hasAnyRole("USER", "ADMIN")

// Permitir acceso a otras rutas /api/** SOLO a ADMIN (ajusta según tus necesidades)
                                .requestMatchers("/api/**").hasRole("ADMIN")

                                .anyRequest().authenticated()
                )
                // Configuración para sesiones sin estado (sin cookies de sesión)
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)  // Autenticación con JWT
                .addFilterBefore(JWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
