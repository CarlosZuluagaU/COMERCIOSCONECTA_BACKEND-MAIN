package com.comerciosconecta.backend.dto;


import java.util.Objects;

public class FactusCustomerDto {

    private String identification;              // "123456789"
    private String dv;                          // "3"

    private String company;                     // ""
    private String trade_name;                  // ""

    private String names;                       // "Alan Turing"

    private String address;                     // "calle 1 # 2-68"
    private String email;                       // "alanturing@enigmasas.com"
    private String phone;                       // "1234567890"

    private Integer legal_organization_id;      // 2
    private Integer tribute_id;                 // 21
    private Integer identification_document_id; // 3
    private Integer municipality_id;            // 980

    // Constructor vacío
    public FactusCustomerDto() {
    }

    // Constructor completo
    public FactusCustomerDto(String identification, String dv, String company, String trade_name,
                             String names, String address, String email, String phone,
                             Integer legal_organization_id, Integer tribute_id,
                             Integer identification_document_id, Integer municipality_id) {
        this.identification = identification;
        this.dv = dv;
        this.company = company;
        this.trade_name = trade_name;
        this.names = names;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.legal_organization_id = legal_organization_id;
        this.tribute_id = tribute_id;
        this.identification_document_id = identification_document_id;
        this.municipality_id = municipality_id;
    }

    // Getters y Setters
    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTrade_name() {
        return trade_name;
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLegal_organization_id() {
        return legal_organization_id;
    }

    public void setLegal_organization_id(Integer legal_organization_id) {
        this.legal_organization_id = legal_organization_id;
    }

    public Integer getTribute_id() {
        return tribute_id;
    }

    public void setTribute_id(Integer tribute_id) {
        this.tribute_id = tribute_id;
    }

    public Integer getIdentification_document_id() {
        return identification_document_id;
    }

    public void setIdentification_document_id(Integer identification_document_id) {
        this.identification_document_id = identification_document_id;
    }

    public Integer getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(Integer municipality_id) {
        this.municipality_id = municipality_id;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactusCustomerDto)) return false;
        FactusCustomerDto that = (FactusCustomerDto) o;
        return Objects.equals(identification, that.identification) &&
                Objects.equals(dv, that.dv) &&
                Objects.equals(company, that.company) &&
                Objects.equals(trade_name, that.trade_name) &&
                Objects.equals(names, that.names) &&
                Objects.equals(address, that.address) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(legal_organization_id, that.legal_organization_id) &&
                Objects.equals(tribute_id, that.tribute_id) &&
                Objects.equals(identification_document_id, that.identification_document_id) &&
                Objects.equals(municipality_id, that.municipality_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identification, dv, company, trade_name, names, address, email, phone,
                legal_organization_id, tribute_id, identification_document_id, municipality_id);
    }

    // toString
    @Override
    public String toString() {
        return "FactusCustomerDto{" +
                "identification='" + identification + '\'' +
                ", dv='" + dv + '\'' +
                ", company='" + company + '\'' +
                ", trade_name='" + trade_name + '\'' +
                ", names='" + names + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", legal_organization_id=" + legal_organization_id +
                ", tribute_id=" + tribute_id +
                ", identification_document_id=" + identification_document_id +
                ", municipality_id=" + municipality_id +
                '}';
    }
}
