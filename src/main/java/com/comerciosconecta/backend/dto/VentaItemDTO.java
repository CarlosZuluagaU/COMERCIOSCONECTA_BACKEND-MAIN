package com.comerciosconecta.backend.dto;

public class VentaItemDTO {
    private Long id;
    private String codigoProducto;
    private String nombre;
    private Integer cantidad;
    private Double precioTotal;
    private Double porcentajeIva;

    // Constructores
    public VentaItemDTO() {}

    public VentaItemDTO(Long id, String codigoProducto, String nombre, Integer cantidad,
                        Double precioTotal, Double porcentajeIva) {
        this.id = id;
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.porcentajeIva = porcentajeIva;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Double getPorcentajeIva() { return porcentajeIva; }
    public void setPorcentajeIva(Double porcentajeIva) { this.porcentajeIva = porcentajeIva; }
}