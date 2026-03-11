package com.comerciosconecta.backend.dto;



import java.util.Objects;

public class FactusAllowanceChargeDto {

    private String concept_type;   // "03"
    private Boolean is_surcharge;  // true
    private String reason;         // "Propina"

    private Double base_amount;    // 90000.0
    private Double amount;         // 9000.0

    // Constructor vacío
    public FactusAllowanceChargeDto() {
    }

    // Constructor completo
    public FactusAllowanceChargeDto(String concept_type, Boolean is_surcharge, String reason,
                                    Double base_amount, Double amount) {
        this.concept_type = concept_type;
        this.is_surcharge = is_surcharge;
        this.reason = reason;
        this.base_amount = base_amount;
        this.amount = amount;
    }

    // Getters y Setters
    public String getConcept_type() {
        return concept_type;
    }

    public void setConcept_type(String concept_type) {
        this.concept_type = concept_type;
    }

    public Boolean getIs_surcharge() {
        return is_surcharge;
    }

    public void setIs_surcharge(Boolean is_surcharge) {
        this.is_surcharge = is_surcharge;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getBase_amount() {
        return base_amount;
    }

    public void setBase_amount(Double base_amount) {
        this.base_amount = base_amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactusAllowanceChargeDto)) return false;
        FactusAllowanceChargeDto that = (FactusAllowanceChargeDto) o;
        return Objects.equals(concept_type, that.concept_type) &&
                Objects.equals(is_surcharge, that.is_surcharge) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(base_amount, that.base_amount) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concept_type, is_surcharge, reason, base_amount, amount);
    }

    // toString
    @Override
    public String toString() {
        return "FactusAllowanceChargeDto{" +
                "concept_type='" + concept_type + '\'' +
                ", is_surcharge=" + is_surcharge +
                ", reason='" + reason + '\'' +
                ", base_amount=" + base_amount +
                ", amount=" + amount +
                '}';
    }
}
