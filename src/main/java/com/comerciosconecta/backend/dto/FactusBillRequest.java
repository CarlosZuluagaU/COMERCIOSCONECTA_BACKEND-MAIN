package com.comerciosconecta.backend.dto;

import java.util.List;



import java.util.List;
import java.util.Objects;

public class FactusBillRequest {

    private String document;                 // "01"
    private Integer numbering_range_id;      // 8
    private String reference_code;           // "fact0022025"
    private String observation;              // ""
    private Integer payment_method_code;     // 10

    private FactusEstablishmentDto establishment;
    private FactusCustomerDto customer;
    private List<FactusItemDto> items;
    private List<FactusAllowanceChargeDto> allowance_charges;

    // Constructor vacío
    public FactusBillRequest() {
    }

    // Constructor completo
    public FactusBillRequest(String document, Integer numbering_range_id, String reference_code, String observation,
                             Integer payment_method_code, FactusEstablishmentDto establishment,
                             FactusCustomerDto customer, List<FactusItemDto> items,
                             List<FactusAllowanceChargeDto> allowance_charges) {
        this.document = document;
        this.numbering_range_id = numbering_range_id;
        this.reference_code = reference_code;
        this.observation = observation;
        this.payment_method_code = payment_method_code;
        this.establishment = establishment;
        this.customer = customer;
        this.items = items;
        this.allowance_charges = allowance_charges;
    }

    // Getters y Setters
    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getNumbering_range_id() {
        return numbering_range_id;
    }

    public void setNumbering_range_id(Integer numbering_range_id) {
        this.numbering_range_id = numbering_range_id;
    }

    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Integer getPayment_method_code() {
        return payment_method_code;
    }

    public void setPayment_method_code(Integer payment_method_code) {
        this.payment_method_code = payment_method_code;
    }

    public FactusEstablishmentDto getEstablishment() {
        return establishment;
    }

    public void setEstablishment(FactusEstablishmentDto establishment) {
        this.establishment = establishment;
    }

    public FactusCustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(FactusCustomerDto customer) {
        this.customer = customer;
    }

    public List<FactusItemDto> getItems() {
        return items;
    }

    public void setItems(List<FactusItemDto> items) {
        this.items = items;
    }

    public List<FactusAllowanceChargeDto> getAllowance_charges() {
        return allowance_charges;
    }

    public void setAllowance_charges(List<FactusAllowanceChargeDto> allowance_charges) {
        this.allowance_charges = allowance_charges;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactusBillRequest)) return false;
        FactusBillRequest that = (FactusBillRequest) o;
        return Objects.equals(document, that.document) &&
                Objects.equals(numbering_range_id, that.numbering_range_id) &&
                Objects.equals(reference_code, that.reference_code) &&
                Objects.equals(observation, that.observation) &&
                Objects.equals(payment_method_code, that.payment_method_code) &&
                Objects.equals(establishment, that.establishment) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(items, that.items) &&
                Objects.equals(allowance_charges, that.allowance_charges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, numbering_range_id, reference_code, observation,
                payment_method_code, establishment, customer, items, allowance_charges);
    }

    // toString
    @Override
    public String toString() {
        return "FactusBillRequest{" +
                "document='" + document + '\'' +
                ", numbering_range_id=" + numbering_range_id +
                ", reference_code='" + reference_code + '\'' +
                ", observation='" + observation + '\'' +
                ", payment_method_code=" + payment_method_code +
                ", establishment=" + establishment +
                ", customer=" + customer +
                ", items=" + items +
                ", allowance_charges=" + allowance_charges +
                '}';
    }
}

