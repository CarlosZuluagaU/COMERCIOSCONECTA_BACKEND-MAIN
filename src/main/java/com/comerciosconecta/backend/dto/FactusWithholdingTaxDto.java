package com.comerciosconecta.backend.dto;


import java.util.Objects;

public class FactusWithholdingTaxDto {

    private String code;                 // "06"
    private Double withholding_tax_rate; // 7.38

    // Constructor vacío
    public FactusWithholdingTaxDto() {
    }

    // Constructor completo
    public FactusWithholdingTaxDto(String code, Double withholding_tax_rate) {
        this.code = code;
        this.withholding_tax_rate = withholding_tax_rate;
    }

    // Getters y Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getWithholding_tax_rate() {
        return withholding_tax_rate;
    }

    public void setWithholding_tax_rate(Double withholding_tax_rate) {
        this.withholding_tax_rate = withholding_tax_rate;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactusWithholdingTaxDto)) return false;
        FactusWithholdingTaxDto that = (FactusWithholdingTaxDto) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(withholding_tax_rate, that.withholding_tax_rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, withholding_tax_rate);
    }

    // toString
    @Override
    public String toString() {
        return "FactusWithholdingTaxDto{" +
                "code='" + code + '\'' +
                ", withholding_tax_rate=" + withholding_tax_rate +
                '}';
    }
}

