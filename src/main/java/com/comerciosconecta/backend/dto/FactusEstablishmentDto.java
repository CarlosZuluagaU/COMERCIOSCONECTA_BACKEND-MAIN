package com.comerciosconecta.backend.dto;



import java.util.Objects;

public class FactusEstablishmentDto {

    private String name;             // "SuperMarket"
    private String address;          // "calle 10 # 3-13"
    private String phone_number;     // "0987654321"
    private String email;            // "supermarket@gmail.com"
    private Integer municipality_id; // 980

    // Constructor vacío
    public FactusEstablishmentDto() {
    }

    // Constructor completo
    public FactusEstablishmentDto(String name, String address, String phone_number,
                                  String email, Integer municipality_id) {
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.municipality_id = municipality_id;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(o instanceof FactusEstablishmentDto)) return false;
        FactusEstablishmentDto that = (FactusEstablishmentDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(phone_number, that.phone_number) &&
                Objects.equals(email, that.email) &&
                Objects.equals(municipality_id, that.municipality_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone_number, email, municipality_id);
    }

    // toString
    @Override
    public String toString() {
        return "FactusEstablishmentDto{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", municipality_id=" + municipality_id +
                '}';
    }
}
