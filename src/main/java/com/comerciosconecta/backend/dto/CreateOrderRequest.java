package com.comerciosconecta.backend.dto;



import java.util.List;

public class CreateOrderRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Long totalInCents;
    private List<Item> items;

    // Getters y Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Long getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(Long totalInCents) {
        this.totalInCents = totalInCents;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // Clase interna Item
    public static class Item {
        private Long productoId;
        private String nombre;
        private Integer cantidad;
        private Long priceInCents;
        private Integer ivaPercentage;
        private Long subtotalInCents;

        // Getters y Setters
        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Long getPriceInCents() {
            return priceInCents;
        }

        public void setPriceInCents(Long priceInCents) {
            this.priceInCents = priceInCents;
        }

        public Integer getIvaPercentage() {
            return ivaPercentage;
        }

        public void setIvaPercentage(Integer ivaPercentage) {
            this.ivaPercentage = ivaPercentage;
        }

        public Long getSubtotalInCents() {
            return subtotalInCents;
        }

        public void setSubtotalInCents(Long subtotalInCents) {
            this.subtotalInCents = subtotalInCents;
        }
    }
}

