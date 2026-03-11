package com.comerciosconecta.backend.dto;

import com.comerciosconecta.backend.entity.EstadoProveedor;
import com.comerciosconecta.backend.entity.TipoProveedor;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDTO {

    private Long id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private TipoProveedor tipo;
    private EstadoProveedor estado;
    private List<String> productos = new ArrayList<>();

    public ProveedorDTO() {}

    public ProveedorDTO(Long id, String nombre, String contacto, String telefono,
                        String email, String direccion, TipoProveedor tipo,
                        EstadoProveedor estado, List<String> productos) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
        this.productos = productos;
    }

    // ===== Getters y Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoProveedor getTipo() {
        return tipo;
    }

    public void setTipo(TipoProveedor tipo) {
        this.tipo = tipo;
    }

    public EstadoProveedor getEstado() {
        return estado;
    }

    public void setEstado(EstadoProveedor estado) {
        this.estado = estado;
    }

    public List<String> getProductos() {
        return productos;
    }

    public void setProductos(List<String> productos) {
        this.productos = productos;
    }
}
