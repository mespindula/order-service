package com.orderservice.infrastructure.repository.persistence.db;

import com.orderservice.infrastructure.data.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDbRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByExternalId(String externalId);
}
