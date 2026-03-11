package com.comerciosconecta.backend.service;

import com.comerciosconecta.backend.entity.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente registrarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    Cliente obtenerClientePorId(Long id);
    Cliente actualizarCliente(Long id, Cliente cliente);
    void eliminarCliente(Long id);
}
