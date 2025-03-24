package com.orderservice.domain.repository;

import com.orderservice.domain.data.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    Order findByExternalId(String externalId);
    Optional<Order> findById(Long id);
    List<Order> findAllOrders();
}
