package com.comerciosconecta.backend.service;

import com.comerciosconecta.backend.dto.ProductoDTO;
import com.comerciosconecta.backend.entity.Producto;
import com.comerciosconecta.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // ------------------- Métodos privados de ayuda -------------------

    private ProductoDTO convertirADTO(Producto producto) {
        return new ProductoDTO(producto);
    }

    private Producto convertirAEntidad(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setReferencia(dto.getReferencia());
        producto.setPrecioCompra(dto.getPrecioCompra());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setIva(dto.getIva());

        producto.setCategoria(dto.getCategoria());
        producto.setMarca(dto.getMarca());
        producto.setAlmacenamiento(dto.getAlmacenamiento());
        producto.setEstado(dto.getEstado());
        producto.setStock(dto.getStock());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setProveedor(dto.getProveedor());
        producto.setDescripcion(dto.getDescripcion());
        producto.setFechaActualizacion(dto.getFechaActualizacion());
        producto.setUsuarioActualizacion(dto.getUsuarioActualizacion());
        return producto;
    }

    // ------------------- CRUD -------------------

    // Crear producto
    public ProductoDTO crearProducto(ProductoDTO dto) {
        if (dto.getPrecioVenta() <= dto.getPrecioCompra()) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor al de compra");
        }
        Producto producto = convertirAEntidad(dto);
        producto.setFechaActualizacion(LocalDateTime.now());
        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }

    // Listar todos los productos
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Obtener producto por ID
    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return convertirADTO(producto);
    }

    // Actualizar producto existente
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        if (dto.getPrecioVenta() <= dto.getPrecioCompra()) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor al de compra");
        }

        producto.setNombre(dto.getNombre());
        producto.setReferencia(dto.getReferencia());
        producto.setPrecioCompra(dto.getPrecioCompra());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setIva(dto.getIva());

        producto.setCategoria(dto.getCategoria());
        producto.setMarca(dto.getMarca());
        producto.setAlmacenamiento(dto.getAlmacenamiento());
        producto.setEstado(dto.getEstado());
        producto.setStock(dto.getStock());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setProveedor(dto.getProveedor());
        producto.setDescripcion(dto.getDescripcion());
        producto.setFechaActualizacion(LocalDateTime.now());
        producto.setUsuarioActualizacion(dto.getUsuarioActualizacion());

        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }

    // Eliminar producto
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }
}
