package com.tribuna360.controller;

import com.tribuna360.DTOsecurity.AuthResponse;
import com.tribuna360.DTOsecurity.LoginRequest;
import com.tribuna360.DTOsecurity.RegisterRequest;
import com.tribuna360.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login de usuario", description = "Permite al usuario iniciar sesión con su cédula y contraseña",
            security = {})
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Registro de usuario", description = "Permite registrar un nuevo usuario en el sistema",
            security = {})
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
