package com.tribuna360.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";  // Clave secreta para firmar el JWT

    // Este método genera un token JWT para un usuario basado en sus detalles.
    // En este caso, se usa `CustomUserDetails` como tipo de usuario.
    public String generateToken(UserDetails usuario) {
        return generateToken(new HashMap<>(), usuario);
    }

    // Genera el token JWT usando una clave secreta y un conjunto de "extra claims".
    private String generateToken(Map<String, Object> extraClaims, UserDetails usuario) {
        return Jwts
                .builder()
                // Se agregan las reclamaciones adicionales, si las hay
                .setClaims(extraClaims)
                // Usamos la cédula como el "subject" del token
                .setSubject(usuario.getUsername()) // Aquí el username de `UserDetails` será la cédula
                // Establece la fecha de emisión del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Establece la fecha de expiración del token (1 hora de validez)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                // Firma el token usando la clave secreta y el algoritmo HS256
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact(); // Genera el token JWT
    }

    // Este método devuelve la clave secreta utilizada para firmar los tokens JWT
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // Genera la clave HMAC para la firma del token
    }

    // Obtener el nombre de usuario (en este caso, la cédula) desde el token JWT
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject); // Obtiene el "subject" (cedula) del token
    }

    // Valida si el token JWT es válido comprobando si el nombre de usuario coincide y si el token no ha expirado
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Valida el token
    }

    // Obtiene todas las reclamaciones del token JWT (como el nombre de usuario y la fecha de expiración)
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey()) // Usa la clave secreta para verificar la firma
                .build()
                .parseClaimsJws(token) // Analiza el JWT
                .getBody();  // Devuelve las reclamaciones (información contenida en el token)
    }

    // Obtiene una reclamación específica del token JWT usando un `claimsResolver`
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token); // Obtiene todas las reclamaciones
        return claimsResolver.apply(claims);  // Aplica la función para obtener la reclamación específica
    }

    // Verifica si el token JWT ha expirado comparando la fecha de expiración con la fecha actual
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());  // Si la fecha de expiración es anterior a la fecha actual, el token ha expirado
    }

    // Obtiene la fecha de expiración del token JWT
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration); // Obtiene la fecha de expiración de las reclamaciones del token
    }
}
