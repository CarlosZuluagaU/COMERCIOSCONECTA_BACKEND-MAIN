package com.comerciosconecta.backend.controller;

import com.comerciosconecta.backend.dto.CompraDTO;
import com.comerciosconecta.backend.dto.CompraResumenDTO;
import com.comerciosconecta.backend.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<CompraDTO> registrarCompra(@RequestBody CompraDTO dto) {
        return ResponseEntity.ok(compraService.registrarCompra(dto));
    }

    @GetMapping
    public ResponseEntity<List<CompraResumenDTO>> listarCompras() {
        return ResponseEntity.ok(compraService.listarCompras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> obtenerCompra(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.obtenerCompra(id));
    }


}
