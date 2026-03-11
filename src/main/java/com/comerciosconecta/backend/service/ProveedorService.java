package com.comerciosconecta.backend.service;

import com.comerciosconecta.backend.dto.ProveedorDTO;
import com.comerciosconecta.backend.entity.Proveedor;
import com.comerciosconecta.backend.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<ProveedorDTO> listarProveedores() {
        return proveedorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProveedorDTO guardarProveedor(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor(
                dto.getNombre(),
                dto.getContacto(),
                dto.getTelefono(),
                dto.getEmail(),
                dto.getDireccion(),
                dto.getTipo(),
                dto.getEstado(),
                dto.getProductos()
        );

        Proveedor saved = proveedorRepository.save(proveedor);
        return convertToDTO(saved);
    }

    public ProveedorDTO actualizarProveedor(Long id, ProveedorDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedor.setNombre(dto.getNombre());
        proveedor.setContacto(dto.getContacto());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTipo(dto.getTipo());
        proveedor.setEstado(dto.getEstado());
        proveedor.setProductos(dto.getProductos());
        return convertToDTO(proveedorRepository.save(proveedor));
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    private ProveedorDTO convertToDTO(Proveedor proveedor) {
        return new ProveedorDTO(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getContacto(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.getDireccion(),
                proveedor.getTipo(),
                proveedor.getEstado(),
                proveedor.getProductos()
        );
    }
}
