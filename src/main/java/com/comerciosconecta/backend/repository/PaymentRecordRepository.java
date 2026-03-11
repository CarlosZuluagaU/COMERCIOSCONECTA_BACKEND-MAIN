package com.comerciosconecta.backend.repository;

import com.comerciosconecta.backend.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
    Optional<PaymentRecord> findByWompiTransactionId(String wompiTransactionId);
}
