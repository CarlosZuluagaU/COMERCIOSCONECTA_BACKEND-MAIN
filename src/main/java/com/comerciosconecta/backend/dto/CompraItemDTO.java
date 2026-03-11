package com.comerciosconecta.backend.dto;

public class CompraItemDTO {
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Integer iva;

    public CompraItemDTO() {}

    // ===== Getters y Setters =====
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Integer getIva() { return iva; }
    public void setIva(Integer iva) { this.iva = iva; }
}
