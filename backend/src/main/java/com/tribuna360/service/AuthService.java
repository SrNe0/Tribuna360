package com.tribuna360.service;

import com.tribuna360.DTOsecurity.AuthResponse;
import com.tribuna360.DTOsecurity.LoginRequest;
import com.tribuna360.DTOsecurity.RegisterRequest;
import com.tribuna360.enums.UsuarioRolEnum;
import com.tribuna360.exception.ContrasenaIncorrectaException;
import com.tribuna360.exception.UsuarioNoEncontradoException;
import com.tribuna360.security.CustomUserDetails;
import com.tribuna360.security.JWTUtil;
import com.tribuna360.model.Usuario;
import com.tribuna360.repository.UsuarioRepository;
import com.tribuna360.security.CustomUserDetailsService;
import com.tribuna360.exception.UserAlreadyExistsException; // Excepción personalizada
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JWTUtil jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Método de login: Recibe un request de login, valida el usuario y genera un token JWT.
     */
    public AuthResponse login(LoginRequest request) {
        // Buscar al usuario en la base de datos por cédula
        Usuario usuario = usuarioRepository.findByCedula(request.getCedula())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        // Verificar la contraseña con la que se intenta autenticar
        if (!new BCryptPasswordEncoder().matches(request.getPassword(), usuario.getContrasena())) {
            throw new ContrasenaIncorrectaException("Contraseña incorrecta");
        }

        // Autentica al usuario usando el AuthenticationManager
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCedula(), request.getPassword()));

        // Recupera los detalles del usuario
        UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(request.getCedula());  // Obtener CustomUserDetails

        // Genera un token JWT para el usuario autenticado.
        String token = jwtService.generateToken(customUserDetails);

        // Obtiene el ID del usuario y devuelve una respuesta con el token y el ID.
        long id = ((com.tribuna360.security.CustomUserDetails) customUserDetails).getUsuario().getIdUsuario();

        return AuthResponse.builder()
                .token(token)
                .id(id)
                .build();
    }


    /**
     * Método de registro: Recibe un request de registro, crea un nuevo usuario, y genera un token JWT.
     */
    public AuthResponse register(RegisterRequest request) {
        // Verifica si el usuario ya existe con la cédula proporcionada.
        if (usuarioRepository.existsByCedula(request.getCedula())) {
            throw new UserAlreadyExistsException("Usuario ya existe con esa cédula");
        }

        // Verifica si el usuario ya existe con el email proporcionado.
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Usuario ya existe con ese email");
        }

        // Verifica si el usuario ya existe con el teléfono proporcionado.
        if (usuarioRepository.existsByTelefono(request.getTelefono())) {
            throw new UserAlreadyExistsException("Usuario ya existe con ese teléfono");
        }

        // Crea un nuevo objeto Usuario con los datos proporcionados en el request.
        Usuario usuario = Usuario.builder()
                .cedula(request.getCedula())
                .nombre(request.getNombre())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .contrasena(passwordEncoder.encode(request.getPassword())) // Encripta la contraseña
                .fechaRegistro(java.time.LocalDate.now()) // Fecha de registro
                .rol(UsuarioRolEnum.USER) // Rol por defecto: USER
                .build();

        // Guarda el nuevo usuario en la base de datos.
        usuarioRepository.save(usuario);

        // Genera un token JWT para el nuevo usuario. Usamos CustomUserDetails para obtener la información del usuario
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getCedula());
        String token = jwtService.generateToken(customUserDetails);

        // Obtiene el ID del nuevo usuario.
        long id = usuario.getIdUsuario();

        // Retorna el token y el ID en la respuesta.
        return AuthResponse.builder()
                .token(token)
                .id(id)
                .build();
    }
}
