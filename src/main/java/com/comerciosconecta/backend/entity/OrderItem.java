package com.comerciosconecta.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;
    private String nombre;
    private Integer cantidad;
    private Long priceInCents;
    private Integer ivaPercentage;
    private Long subtotalInCents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    // Constructor vacío
    public OrderItem() {
    }

    // Constructor con parámetros
    public OrderItem(Long productoId, String nombre, Integer cantidad,
                     Long priceInCents, Integer ivaPercentage, Long subtotalInCents) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.priceInCents = priceInCents;
        this.ivaPercentage = ivaPercentage;
        this.subtotalInCents = subtotalInCents;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(Long priceInCents) {
        this.priceInCents = priceInCents;
    }

    public Integer getIvaPercentage() {
        return ivaPercentage;
    }

    public void setIvaPercentage(Integer ivaPercentage) {
        this.ivaPercentage = ivaPercentage;
    }

    public Long getSubtotalInCents() {
        return subtotalInCents;
    }

    public void setSubtotalInCents(Long subtotalInCents) {
        this.subtotalInCents = subtotalInCents;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}

