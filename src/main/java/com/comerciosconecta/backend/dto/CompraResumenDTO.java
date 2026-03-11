package com.comerciosconecta.backend.dto;

import com.comerciosconecta.backend.entity.EstadoCompra;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;


public class CompraResumenDTO {

    private Long id;
    private String numeroFactura;
    private String proveedor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaCompra;
    private Double total;
    private EstadoCompra estado;
    private Integer items;

    public CompraResumenDTO() {}

    public CompraResumenDTO(Long id, String numeroFactura, String proveedor, LocalDate fechaCompra,
                            Double total, EstadoCompra estado, Integer items) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
        this.total = total;
        this.estado = estado;
        this.items = items;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public EstadoCompra getEstado() { return estado; }
    public void setEstado(EstadoCompra estado) { this.estado = estado; }

    public Integer getItems() { return items; }
    public void setItems(Integer items) { this.items = items; }
}
