package com.comerciosconecta.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroFactura;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    private LocalDate fechaCompra;
    private Double subtotal;
    private Integer iva;
    private Double total;

    @Enumerated(EnumType.STRING)
    private EstadoCompra estado;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompraItem> items = new ArrayList<>();

    // ===== Constructores =====
    public Compra() {}
    public Compra(String numeroFactura, Proveedor proveedor, LocalDate fechaCompra,
                  Double subtotal, Integer iva, Double total, EstadoCompra estado) {
        this.numeroFactura = numeroFactura;
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
    }

    // ===== Getters y Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

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

    public List<CompraItem> getItems() { return items; }
    public void setItems(List<CompraItem> items) { this.items = items; }

    public void addItem(CompraItem item) {
        items.add(item);
        item.setCompra(this);
    }

    public void removeItem(CompraItem item) {
        items.remove(item);
        item.setCompra(null);
    }
}
