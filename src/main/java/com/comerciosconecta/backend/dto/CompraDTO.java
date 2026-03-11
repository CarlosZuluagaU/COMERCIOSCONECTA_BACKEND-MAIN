package com.comerciosconecta.backend.dto;

import com.comerciosconecta.backend.entity.EstadoCompra;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;


public class CompraDTO {

    private Long id;
    private String numeroFactura;
    private Long proveedorId;

    private LocalDate fechaCompra;
    private Double subtotal;
    private Integer iva;
    private Double total;
    private EstadoCompra estado;
    private List<CompraItemDTO> items = new ArrayList<>();

    public CompraDTO() {}

    // ===== Getters y Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public Long getProveedorId() { return proveedorId; }
    public void setProveedorId(Long proveedorId) { this.proveedorId = proveedorId; }

    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Integer getIva() { return iva; }
    public void setIva(Integer iva) { this.iva = iva; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public EstadoCompra getEstado() { return estado; }
    public void setEstado(EstadoCompra estado) { this.estado = estado; }

    public List<CompraItemDTO> getItems() { return items; }
    public void setItems(List<CompraItemDTO> items) { this.items = items; }
}
