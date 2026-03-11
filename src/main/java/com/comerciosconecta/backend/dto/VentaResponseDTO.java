package com.comerciosconecta.backend.dto;

import java.util.List;

public class VentaResponseDTO {

    private Long id;
    private String estado;
    private String clienteNombre;
    private List<VentaItemDTO> items;

    public VentaResponseDTO() {
    }

    public VentaResponseDTO(Long id, String estado, String clienteNombre, List<VentaItemDTO> items) {
        this.id = id;
        this.estado = estado;
        this.clienteNombre = clienteNombre;
        this.items = items;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public List<VentaItemDTO> getItems() {
        return items;
    }

    public void setItems(List<VentaItemDTO> items) {
        this.items = items;
    }
}
