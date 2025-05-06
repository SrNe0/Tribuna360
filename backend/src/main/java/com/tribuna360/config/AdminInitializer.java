package com.tribuna360.config;

import com.tribuna360.model.Usuario;
import com.tribuna360.enums.UsuarioRolEnum;
import com.tribuna360.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {  // Si no hay usuarios en la base de datos
            Usuario admin = new Usuario();
            admin.setCedula("admin-0001");
            admin.setNombre("Admin");
            admin.setEmail("admin@tribuna360.com");
            admin.setTelefono("333-333-3333");
            admin.setContrasena(new BCryptPasswordEncoder().encode("adminpassword"));  // Ciframos la contraseña
            admin.setRol(UsuarioRolEnum.ADMIN);
            usuarioRepository.save(admin);
            System.out.println("Usuario admin creado!");
        }
    }
}
