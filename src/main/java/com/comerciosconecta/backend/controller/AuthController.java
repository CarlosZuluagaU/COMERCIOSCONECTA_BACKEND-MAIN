package com.comerciosconecta.backend.controller;

import com.comerciosconecta.backend.dto.*;
import com.comerciosconecta.backend.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserDetailsServiceImpl userDetailsService;

    // ===== LOGIN =====
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        UserDetails ud = (UserDetails) auth.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(ud);
        String refreshToken = jwtUtil.generateRefreshToken(ud);

        // Devuelve ambos tokens en el body
        return ResponseEntity.ok(new AuthResponse(accessToken, jwtUtil.getAccessExpirationMs(), refreshToken));
    }

    // ===== REFRESH TOKEN =====
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refresh = body.get("refreshToken");
        if (refresh == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            String username = jwtUtil.extractUsername(refresh);
            UserDetails ud = userDetailsService.loadUserByUsername(username);

            if (!jwtUtil.isTokenExpired(refresh) && jwtUtil.validateToken(refresh, ud)) {
                String newAccess = jwtUtil.generateAccessToken(ud);

                return ResponseEntity.ok(new AuthResponse(newAccess, jwtUtil.getAccessExpirationMs(), refresh));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // ===== LOGOUT =====
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        return ResponseEntity.ok().build();
    }
}
