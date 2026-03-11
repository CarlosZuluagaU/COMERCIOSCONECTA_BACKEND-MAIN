package com.comerciosconecta.backend.dto;


import java.util.List;


import java.util.List;
import java.util.Objects;

public class FactusItemDto {

    private String code_reference;    // "12345"
    private String name;              // "producto de prueba"

    private Integer quantity;         // 1
    private Double discount_rate;     // 20

    private Double price;             // 50000.0
    private String tax_rate;          // "19.00"

    private Integer unit_measure_id;  // 70
    private Integer standard_code_id; // 1

    private Integer is_excluded;      // 0
    private Integer tribute_id;       // 1

    private List<FactusWithholdingTaxDto> withholding_taxes;

    // Constructor vacío
    public FactusItemDto() {
    }

    // Constructor completo
    public FactusItemDto(String code_reference, String name, Integer quantity, Double discount_rate,
                         Double price, String tax_rate, Integer unit_measure_id, Integer standard_code_id,
                         Integer is_excluded, Integer tribute_id, List<FactusWithholdingTaxDto> withholding_taxes) {
        this.code_reference = code_reference;
        this.name = name;
        this.quantity = quantity;
        this.discount_rate = discount_rate;
        this.price = price;
        this.tax_rate = tax_rate;
        this.unit_measure_id = unit_measure_id;
        this.standard_code_id = standard_code_id;
        this.is_excluded = is_excluded;
        this.tribute_id = tribute_id;
        this.withholding_taxes = withholding_taxes;
    }

    // Getters y Setters
    public String getCode_reference() {
        return code_reference;
    }

    public void setCode_reference(String code_reference) {
        this.code_reference = code_reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(Double discount_rate) {
        this.discount_rate = discount_rate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public Integer getUnit_measure_id() {
        return unit_measure_id;
    }

    public void setUnit_measure_id(Integer unit_measure_id) {
        this.unit_measure_id = unit_measure_id;
    }

    public Integer getStandard_code_id() {
        return standard_code_id;
    }

    public void setStandard_code_id(Integer standard_code_id) {
        this.standard_code_id = standard_code_id;
    }

    public Integer getIs_excluded() {
        return is_excluded;
    }

    public void setIs_excluded(Integer is_excluded) {
        this.is_excluded = is_excluded;
    }

    public Integer getTribute_id() {
        return tribute_id;
    }

    public void setTribute_id(Integer tribute_id) {
        this.tribute_id = tribute_id;
    }

    public List<FactusWithholdingTaxDto> getWithholding_taxes() {
        return withholding_taxes;
    }

    public void setWithholding_taxes(List<FactusWithholdingTaxDto> withholding_taxes) {
        this.withholding_taxes = withholding_taxes;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactusItemDto)) return false;
        FactusItemDto that = (FactusItemDto) o;
        return Objects.equals(code_reference, that.code_reference) &&
                Objects.equals(name, that.name) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(discount_rate, that.discount_rate) &&
                Objects.equals(price, that.price) &&
                Objects.equals(tax_rate, that.tax_rate) &&
                Objects.equals(unit_measure_id, that.unit_measure_id) &&
                Objects.equals(standard_code_id, that.standard_code_id) &&
                Objects.equals(is_excluded, that.is_excluded) &&
                Objects.equals(tribute_id, that.tribute_id) &&
                Objects.equals(withholding_taxes, that.withholding_taxes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code_reference, name, quantity, discount_rate, price, tax_rate,
                unit_measure_id, standard_code_id, is_excluded, tribute_id, withholding_taxes);
    }

    // toString
    @Override
    public String toString() {
        return "FactusItemDto{" +
                "code_reference='" + code_reference + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", discount_rate=" + discount_rate +
                ", price=" + price +
                ", tax_rate='" + tax_rate + '\'' +
                ", unit_measure_id=" + unit_measure_id +
                ", standard_code_id=" + standard_code_id +
                ", is_excluded=" + is_excluded +
                ", tribute_id=" + tribute_id +
                ", withholding_taxes=" + withholding_taxes +
                '}';
    }
}
