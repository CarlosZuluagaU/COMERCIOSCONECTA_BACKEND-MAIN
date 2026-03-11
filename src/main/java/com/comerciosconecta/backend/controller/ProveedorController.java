package com.comerciosconecta.backend.controller;

import com.comerciosconecta.backend.dto.ProveedorDTO;
import com.comerciosconecta.backend.service.ProveedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public List<ProveedorDTO> listar() {
        return proveedorService.listarProveedores();
    }

    @PostMapping
    public ProveedorDTO crear(@RequestBody ProveedorDTO dto) {
        return proveedorService.guardarProveedor(dto);
    }

    @PutMapping("/{id}")
    public ProveedorDTO actualizar(@PathVariable Long id, @RequestBody ProveedorDTO dto) {
        return proveedorService.actualizarProveedor(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
    }
}
