package com.comerciosconecta.backend.service;



import com.comerciosconecta.backend.entity.Comercio;
import com.comerciosconecta.backend.repository.ComercioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComercioService {

    @Autowired
    private ComercioRepository comercioRepository;

    public Comercio registrarComercio(Comercio comercio) {
        // Validar duplicados por NIT o correo
        if (comercioRepository.findByNit(comercio.getNit()).isPresent()) {
            throw new RuntimeException("Ya existe un comercio con ese NIT");
        }

        if (comercioRepository.findByEmail(comercio.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un comercio con ese correo electrónico");
        }

        return comercioRepository.save(comercio);
    }

    public List<Comercio> listarComercios() {
        return comercioRepository.findAll();
    }
}

