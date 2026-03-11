package com.comerciosconecta.backend.service.Impl;



import com.comerciosconecta.backend.entity.Cliente;
import com.comerciosconecta.backend.repository.ClienteRepository;
import com.comerciosconecta.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente registrarCliente(Cliente cliente) {
        clienteRepository.findByNumeroDocumento(cliente.getNumeroDocumento())
                .ifPresent(c -> {
                    throw new RuntimeException("El número de documento ya está registrado");
                });
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Cliente existente = obtenerClientePorId(id);
        existente.setTipoDocumento(cliente.getTipoDocumento());
        existente.setNumeroDocumento(cliente.getNumeroDocumento());
        existente.setNombres(cliente.getNombres());
        existente.setApellidos(cliente.getApellidos());
        existente.setTelefono(cliente.getTelefono());
        existente.setCorreo(cliente.getCorreo());
        existente.setDireccion(cliente.getDireccion());
        existente.setCiudad(cliente.getCiudad());
        existente.setEstado(cliente.getEstado());
        return clienteRepository.save(existente);
    }

    @Override
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("El cliente no existe");
        }
        clienteRepository.deleteById(id);
    }
}
