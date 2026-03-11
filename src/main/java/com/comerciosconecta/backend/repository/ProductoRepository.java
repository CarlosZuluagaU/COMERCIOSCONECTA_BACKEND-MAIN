package com.comerciosconecta.backend.repository;

import com.comerciosconecta.backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;



@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar producto por referencia (que es única)
    Optional<Producto> findByReferencia(String referencia);

    // Verificar si existe un producto por nombre
    boolean existsByNombre(String nombre);

    // Verificar si existe un producto por referencia
    boolean existsByReferencia(String referencia);
}

