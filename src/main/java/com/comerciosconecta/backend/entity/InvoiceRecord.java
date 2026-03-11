package com.comerciosconecta.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_records")
public class InvoiceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venta_id")
    private Long ventaId;

    @Column(name = "factus_bill_id")
    private String factusBillId;

    private String number;
    private String status;

    @Column(name = "raw_response", columnDefinition = "TEXT")
    private String rawResponse;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructor vacío
    public InvoiceRecord() {}

    // Constructor sin ID (el que ya tenías)
    public InvoiceRecord(Long ventaId, String factusBillId, String number,
                         String status, String rawResponse) {
        this.ventaId = ventaId;
        this.factusBillId = factusBillId;
        this.number = number;
        this.status = status;
        this.rawResponse = rawResponse;
        this.createdAt = LocalDateTime.now();
    }

    // Constructor completo con ID
    public InvoiceRecord(Long id, Long ventaId, String factusBillId, String number,
                         String status, String rawResponse, LocalDateTime createdAt) {
        this.id = id;
        this.ventaId = ventaId;
        this.factusBillId = factusBillId;
        this.number = number;
        this.status = status;
        this.rawResponse = rawResponse;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public String getFactusBillId() {
        return factusBillId;
    }

    public void setFactusBillId(String factusBillId) {
        this.factusBillId = factusBillId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
