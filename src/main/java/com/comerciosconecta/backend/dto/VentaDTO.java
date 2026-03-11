package com.comerciosconecta.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VentaDTO {
    private Long id;
    private String uuid;
    private String estado;
    private Double totalFactura;
    private String nota;
    private String referencia;
    private String nombreCliente;
    private String numeroDocumentoCliente;
    private LocalDateTime createdAt;
    private List<VentaItemDTO> items;

    // Constructores
    public VentaDTO() {}

    public VentaDTO(Long id, String uuid, String estado, Double totalFactura,
                    String nota, String referencia, String nombreCliente,
                    String numeroDocumentoCliente, LocalDateTime createdAt,
                    List<VentaItemDTO> items) {
        this.id = id;
        this.uuid = uuid;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.nota = nota;
        this.referencia = referencia;
        this.nombreCliente = nombreCliente;
        this.numeroDocumentoCliente = numeroDocumentoCliente;
        this.createdAt = createdAt;
        this.items = items;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getTotalFactura() { return totalFactura; }
    public void setTotalFactura(Double totalFactura) { this.totalFactura = totalFactura; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNumeroDocumentoCliente() { return numeroDocumentoCliente; }
    public void setNumeroDocumentoCliente(String numeroDocumentoCliente) { this.numeroDocumentoCliente = numeroDocumentoCliente; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<VentaItemDTO> getItems() { return items; }
    public void setItems(List<VentaItemDTO> items) { this.items = items; }
}