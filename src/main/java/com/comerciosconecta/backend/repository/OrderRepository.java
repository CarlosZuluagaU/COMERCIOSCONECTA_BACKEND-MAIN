package com.comerciosconecta.backend.repository;

import com.comerciosconecta.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUuid(String uuid);

    List<Order> findAllByOrderByCreatedAtDesc();
}
