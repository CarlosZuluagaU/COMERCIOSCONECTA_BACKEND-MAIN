package com.comerciosconecta.backend.config;

import com.comerciosconecta.backend.entity.Comercio;
import com.comerciosconecta.backend.entity.Rol;
import com.comerciosconecta.backend.entity.Usuario;
import com.comerciosconecta.backend.entity.EstadoGeneral;
import com.comerciosconecta.backend.repository.ComercioRepository;
import com.comerciosconecta.backend.repository.RolRepository;
import com.comerciosconecta.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(ComercioRepository comercioRepo, RolRepository rolRepo,
                           UsuarioRepository usuarioRepo, PasswordEncoder encoder) {
        return args -> {

            // Crear rol admin si no existe
            Rol rolAdmin = rolRepo.findByNombre("ROLE_ADMIN").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombre("ROLE_ADMIN");
                r.setDescripcion("Administrador");
                return rolRepo.save(r);
            });

            // Crear comercio demo si no existe
            Comercio comercioDemo = comercioRepo.count() == 0 ? new Comercio() : null;
            if (comercioDemo != null) {
                comercioDemo.setNombre("Tienda Demo");
                comercioDemo.setNit("900000001");
                comercioDemo.setEmail("demo@tienda.local");

                comercioDemo = comercioRepo.save(comercioDemo);
            }

            // Crear usuario admin demo si no existe
            if (usuarioRepo.findByEmail("alejo@gmail.com").isEmpty()) {
                Usuario u = new Usuario();
                u.setComercio(comercioDemo);
                u.setNombre("Admin Demo");
                u.setEmail("alejo@gmail.com");
                u.setPassword(encoder.encode("12345678"));
                u.setEstado(EstadoGeneral.Activo); // uso del enum
                u.getRoles().add(rolAdmin);

                usuarioRepo.save(u);
            }
        };
    }
}
