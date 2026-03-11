package com.comerciosconecta.backend.repository;

import com.comerciosconecta.backend.entity.InvoiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRecordRepository extends JpaRepository<InvoiceRecord, Long> {

    // Para verificar si ya existe una factura exitosa
    InvoiceRecord findFirstByVentaIdAndStatus(Long ventaId, String status);

    // Para listar facturas ordenadas del más reciente al más antiguo
    List<InvoiceRecord> findByVentaIdOrderByCreatedAtDesc(Long ventaId);

    // También dejo tu método actual por compatibilidad
    List<InvoiceRecord> findByVentaId(Long ventaId);
}
