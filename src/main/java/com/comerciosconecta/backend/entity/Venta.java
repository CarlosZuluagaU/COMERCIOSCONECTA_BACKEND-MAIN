package com.comerciosconecta.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos para control interno
    private String uuid;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Campos necesarios para Factus
    private String nota;
    private String referencia; // Para reference_code

    private Double subtotal;
    private Double totalIva;
    private Double totalFactura;

    private String metodoPago;
    private Integer paymentMethodCode; // Código para Factus (ej: 10)

    // Datos del cliente (se envían dentro de "customer")
    private String tipoDocumentoCliente;
    private String numeroDocumentoCliente;
    private String nombreCliente;
    private String telefonoCliente;
    private String emailCliente;
    private String direccionCliente; // Nueva dirección del cliente
    private String dvCliente; // Dígito de verificación

    // Campos adicionales para mapeo con Factus
    private Integer legalOrganizationId; // legal_organization_id
    private Integer tributeId; // tribute_id
    private Integer identificationDocumentId; // identification_document_id
    private Integer municipalityId; // municipality_id

    // Datos del establecimiento (podrían ser fijos o por venta)
    private String establecimientoNombre;
    private String establecimientoDireccion;
    private String establecimientoTelefono;
    private String establecimientoEmail;
    private Integer establecimientoMunicipioId;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VentaItem> items;

    // Constructores
    public Venta() {
        this.uuid = UUID.randomUUID().toString();
        this.estado = "CREATED";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getTotalIva() { return totalIva; }
    public void setTotalIva(Double totalIva) { this.totalIva = totalIva; }

    public Double getTotalFactura() { return totalFactura; }
    public void setTotalFactura(Double totalFactura) { this.totalFactura = totalFactura; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public Integer getPaymentMethodCode() { return paymentMethodCode; }
    public void setPaymentMethodCode(Integer paymentMethodCode) { this.paymentMethodCode = paymentMethodCode; }

    public String getTipoDocumentoCliente() { return tipoDocumentoCliente; }
    public void setTipoDocumentoCliente(String tipoDocumentoCliente) { this.tipoDocumentoCliente = tipoDocumentoCliente; }

    public String getNumeroDocumentoCliente() { return numeroDocumentoCliente; }
    public void setNumeroDocumentoCliente(String numeroDocumentoCliente) { this.numeroDocumentoCliente = numeroDocumentoCliente; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public String getDireccionCliente() { return direccionCliente; }
    public void setDireccionCliente(String direccionCliente) { this.direccionCliente = direccionCliente; }

    public String getDvCliente() { return dvCliente; }
    public void setDvCliente(String dvCliente) { this.dvCliente = dvCliente; }

    public Integer getLegalOrganizationId() { return legalOrganizationId; }
    public void setLegalOrganizationId(Integer legalOrganizationId) { this.legalOrganizationId = legalOrganizationId; }

    public Integer getTributeId() { return tributeId; }
    public void setTributeId(Integer tributeId) { this.tributeId = tributeId; }

    public Integer getIdentificationDocumentId() { return identificationDocumentId; }
    public void setIdentificationDocumentId(Integer identificationDocumentId) { this.identificationDocumentId = identificationDocumentId; }

    public Integer getMunicipalityId() { return municipalityId; }
    public void setMunicipalityId(Integer municipalityId) { this.municipalityId = municipalityId; }

    public String getEstablecimientoNombre() { return establecimientoNombre; }
    public void setEstablecimientoNombre(String establecimientoNombre) { this.establecimientoNombre = establecimientoNombre; }

    public String getEstablecimientoDireccion() { return establecimientoDireccion; }
    public void setEstablecimientoDireccion(String establecimientoDireccion) { this.establecimientoDireccion = establecimientoDireccion; }

    public String getEstablecimientoTelefono() { return establecimientoTelefono; }
    public void setEstablecimientoTelefono(String establecimientoTelefono) { this.establecimientoTelefono = establecimientoTelefono; }

    public String getEstablecimientoEmail() { return establecimientoEmail; }
    public void setEstablecimientoEmail(String establecimientoEmail) { this.establecimientoEmail = establecimientoEmail; }

    public Integer getEstablecimientoMunicipioId() { return establecimientoMunicipioId; }
    public void setEstablecimientoMunicipioId(Integer establecimientoMunicipioId) { this.establecimientoMunicipioId = establecimientoMunicipioId; }

    public List<VentaItem> getItems() { return items; }
    public void setItems(List<VentaItem> items) {
        if (items != null) {
            items.forEach(i -> i.setVenta(this));
        }
        this.items = items;
    }

    // Métodos de utilidad
    @PrePersist
    protected void onCreate() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "CREATED";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }



}