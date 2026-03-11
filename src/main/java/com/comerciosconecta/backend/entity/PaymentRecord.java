package com.comerciosconecta.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String wompiTransactionId;
    private Long amountInCents;
    private String currency;
    private String status;
    private String paymentMethodType;

    @Column(columnDefinition = "TEXT")
    private String rawResponse;

    private LocalDateTime createdAt;

    // Constructor vacío requerido por JPA
    public PaymentRecord() {
    }

    // Constructor con todos los parámetros
    public PaymentRecord(Long orderId, String wompiTransactionId, Long amountInCents, String currency,
                         String status, String paymentMethodType, String rawResponse) {
        this.orderId = orderId;
        this.wompiTransactionId = wompiTransactionId;
        this.amountInCents = amountInCents;
        this.currency = currency;
        this.status = status;
        this.paymentMethodType = paymentMethodType;
        this.rawResponse = rawResponse;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getWompiTransactionId() {
        return wompiTransactionId;
    }

    public void setWompiTransactionId(String wompiTransactionId) {
        this.wompiTransactionId = wompiTransactionId;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
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
