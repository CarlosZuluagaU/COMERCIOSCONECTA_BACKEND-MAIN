package com.comerciosconecta.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "venta_items")
public class VentaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos requeridos por Factus
    private String codigoProducto;
    private String nombre;
    private Double porcentajeIva;

    private Double precioTotal;
    private Double precioSinImpuestos;

    private Integer cantidad;

    // Nuevos campos para Factus
    private Double descuentoRate; // discount_rate
    private Integer unidadMedidaId; // unit_measure_id
    private Integer standardCodeId; // standard_code_id
    private Integer isExcluded; // is_excluded
    private Integer tributeId; // tribute_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    @JsonIgnore
    private Venta venta;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPorcentajeIva() { return porcentajeIva; }
    public void setPorcentajeIva(Double porcentajeIva) { this.porcentajeIva = porcentajeIva; }

    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Double getPrecioSinImpuestos() { return precioSinImpuestos; }
    public void setPrecioSinImpuestos(Double precioSinImpuestos) { this.precioSinImpuestos = precioSinImpuestos; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getDescuentoRate() { return descuentoRate; }
    public void setDescuentoRate(Double descuentoRate) { this.descuentoRate = descuentoRate; }

    public Integer getUnidadMedidaId() { return unidadMedidaId; }
    public void setUnidadMedidaId(Integer unidadMedidaId) { this.unidadMedidaId = unidadMedidaId; }

    public Integer getStandardCodeId() { return standardCodeId; }
    public void setStandardCodeId(Integer standardCodeId) { this.standardCodeId = standardCodeId; }

    public Integer getIsExcluded() { return isExcluded; }
    public void setIsExcluded(Integer isExcluded) { this.isExcluded = isExcluded; }

    public Integer getTributeId() { return tributeId; }
    public void setTributeId(Integer tributeId) { this.tributeId = tributeId; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }
}