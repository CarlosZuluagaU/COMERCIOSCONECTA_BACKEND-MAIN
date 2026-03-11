package com.comerciosconecta.backend.dto;

import java.time.LocalDateTime;

public class FacturaResponseDTO {
    private Long ventaId;
    private String factusBillId;
    private String number;
    private String status;
    private String rawResponse;
    private LocalDateTime fechaProcesamiento;

    // Constructores
    public FacturaResponseDTO() {
        this.fechaProcesamiento = LocalDateTime.now();
    }

    public FacturaResponseDTO(Long ventaId, String factusBillId, String number, String status, String rawResponse) {
        this();
        this.ventaId = ventaId;
        this.factusBillId = factusBillId;
        this.number = number;
        this.status = status;
        this.rawResponse = rawResponse;
    }

    // Getters y Setters
    public Long getVentaId() { return ventaId; }
    public void setVentaId(Long ventaId) { this.ventaId = ventaId; }

    public String getFactusBillId() { return factusBillId; }
    public void setFactusBillId(String factusBillId) { this.factusBillId = factusBillId; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRawResponse() { return rawResponse; }
    public void setRawResponse(String rawResponse) { this.rawResponse = rawResponse; }

    public LocalDateTime getFechaProcesamiento() { return fechaProcesamiento; }
    public void setFechaProcesamiento(LocalDateTime fechaProcesamiento) { this.fechaProcesamiento = fechaProcesamiento; }
}