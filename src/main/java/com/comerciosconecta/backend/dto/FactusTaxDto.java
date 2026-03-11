package com.comerciosconecta.backend.dto;


public class FactusTaxDto {

    private Integer tax_id;      // 1 = IVA 19%
    private Double tax_rate;     // 19.0

    // Constructor vacío
    public FactusTaxDto() {
    }

    // Constructor completo
    public FactusTaxDto(Integer tax_id, Double tax_rate) {
        this.tax_id = tax_id;
        this.tax_rate = tax_rate;
    }

    // Getters y Setters
    public Integer getTax_id() {
        return tax_id;
    }

    public void setTax_id(Integer tax_id) {
        this.tax_id = tax_id;
    }

    public Double getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(Double tax_rate) {
        this.tax_rate = tax_rate;
    }
}

