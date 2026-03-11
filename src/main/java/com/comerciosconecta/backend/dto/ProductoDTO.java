package com.comerciosconecta.backend.dto;

import com.comerciosconecta.backend.entity.Producto;
import com.comerciosconecta.backend.entity.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La referencia es obligatoria")
    private String referencia;

    @NotNull(message = "El precio de compra es obligatorio")
    @Positive(message = "El precio de compra debe ser mayor que 0")
    private Double precioCompra;

    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio de venta debe ser mayor que 0")
    private Double precioVenta;

    @NotNull(message = "El IVA es obligatorio")
    @Min(value = 0, message = "El IVA no puede ser negativo")
    @Max(value = 100, message = "El IVA no puede superar el 100%")
    private Integer iva;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    private String marca;
    private String almacenamiento;

    @NotNull(message = "El estado es obligatorio")
    private EstadoGeneral estado;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;

    private String proveedor;
    private String descripcion;
    private LocalDateTime fechaActualizacion;
    private String usuarioActualizacion;

    public ProductoDTO() {}

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.referencia = producto.getReferencia();
        this.precioCompra = producto.getPrecioCompra();
        this.precioVenta = producto.getPrecioVenta();
        this.iva = producto.getIva();
        this.categoria = producto.getCategoria();
        this.marca = producto.getMarca();
        this.almacenamiento = producto.getAlmacenamiento();
        this.estado = producto.getEstado();
        this.stock = producto.getStock();
        this.stockMinimo = producto.getStockMinimo();
        this.proveedor = producto.getProveedor();
        this.descripcion = producto.getDescripcion();
        this.fechaActualizacion = producto.getFechaActualizacion();
        this.usuarioActualizacion = producto.getUsuarioActualizacion();
    }

    // ===== Getters y Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }
}
