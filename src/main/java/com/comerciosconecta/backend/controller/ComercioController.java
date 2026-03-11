package com.comerciosconecta.backend.controller;

import com.comerciosconecta.backend.entity.Comercio;
import com.comerciosconecta.backend.service.ComercioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comercios")
@CrossOrigin(origins = "http://localhost:3000") // permitir solicitudes desde el frontend React
public class ComercioController {

    @Autowired
    private ComercioService comercioService;

    @PostMapping
    public ResponseEntity<?> registrarComercio(@RequestBody Comercio comercio) {
        try {
            Comercio nuevo = comercioService.registrarComercio(comercio);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("message", e.getMessage())
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<Comercio>> listarComercios() {
        return ResponseEntity.ok(comercioService.listarComercios());
    }
}
