package com.comerciosconecta.backend.service;

import com.comerciosconecta.backend.dto.CompraDTO;
import com.comerciosconecta.backend.dto.CompraItemDTO;
import com.comerciosconecta.backend.dto.CompraResumenDTO;
import com.comerciosconecta.backend.entity.*;
import com.comerciosconecta.backend.repository.CompraRepository;
import com.comerciosconecta.backend.repository.ProductoRepository;
import com.comerciosconecta.backend.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;

    public CompraService(CompraRepository compraRepository,
                         ProductoRepository productoRepository,
                         ProveedorRepository proveedorRepository) {
        this.compraRepository = compraRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    // Registrar compra
    public CompraDTO registrarCompra(CompraDTO dto) {
        Compra compra = new Compra();
        compra.setNumeroFactura(dto.getNumeroFactura());

        // Buscar proveedor
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + dto.getProveedorId()));
        compra.setProveedor(proveedor);

        compra.setFechaCompra(dto.getFechaCompra());
        compra.setSubtotal(dto.getSubtotal());
        compra.setIva(dto.getIva());
        compra.setTotal(dto.getTotal());
        compra.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoCompra.Pendiente);

        // Crear items
        List<CompraItem> items = dto.getItems().stream().map(itemDto -> {
            Producto producto = productoRepository.findById(itemDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemDto.getProductoId()));

            // Actualizar stock
            producto.setStock(producto.getStock() + itemDto.getCantidad());
            productoRepository.save(producto);

            CompraItem item = new CompraItem();
            item.setProducto(producto);
            item.setCantidad(itemDto.getCantidad());
            item.setPrecioUnitario(itemDto.getPrecioUnitario());
            item.setSubtotal(itemDto.getSubtotal());
            item.setCompra(compra);
            return item;
        }).collect(Collectors.toList());

        compra.setItems(items);

        compraRepository.save(compra);
        dto.setId(compra.getId());
        return dto;
    }

    // Listar compras (resumen)
    public List<CompraResumenDTO> listarCompras() {
        return compraRepository.findAll().stream().map(compra -> {
            CompraResumenDTO dto = new CompraResumenDTO();
            dto.setId(compra.getId());
            dto.setNumeroFactura(compra.getNumeroFactura());
            dto.setProveedor(compra.getProveedor().getNombre());
            dto.setFechaCompra(compra.getFechaCompra());
            dto.setTotal(compra.getTotal());
            dto.setEstado(compra.getEstado());
            dto.setItems(compra.getItems().size());
            return dto;
        }).collect(Collectors.toList());
    }



    // Obtener compra por id
    public CompraDTO obtenerCompra(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada: " + id));

        CompraDTO dto = new CompraDTO();
        dto.setId(compra.getId());
        dto.setNumeroFactura(compra.getNumeroFactura());
        dto.setProveedorId(compra.getProveedor().getId());
        dto.setFechaCompra(compra.getFechaCompra());
        dto.setSubtotal(compra.getSubtotal());
        dto.setIva(compra.getIva());
        dto.setTotal(compra.getTotal());
        dto.setEstado(compra.getEstado());

        List<CompraItemDTO> items = compra.getItems().stream().map(item -> {
            CompraItemDTO itemDto = new CompraItemDTO();
            itemDto.setProductoId(item.getProducto().getId());
            itemDto.setCantidad(item.getCantidad());
            itemDto.setPrecioUnitario(item.getPrecioUnitario());
            itemDto.setSubtotal(item.getSubtotal());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }

}
