package com.comerciosconecta.backend.security;

import com.comerciosconecta.backend.entity.EstadoGeneral;
import com.comerciosconecta.backend.entity.Usuario;
import com.comerciosconecta.backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        var authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getNombre()))
                .collect(Collectors.toList());

        boolean enabled = user.getEstado() == EstadoGeneral.Activo;

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                enabled,       // habilitado solo si está Activo
                true,          // cuenta no expirada
                true,          // credenciales no expiradas
                true,          // cuenta no bloqueada
                authorities
        );
    }
}
