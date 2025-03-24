package com.orderservice.infrastructure.repository.persistence;

import com.orderservice.domain.data.model.Order;
import com.orderservice.domain.repository.OrderRepository;
import com.orderservice.infrastructure.data.mapper.OrderMapper;
import com.orderservice.infrastructure.data.model.OrderEntity;
import com.orderservice.infrastructure.repository.persistence.db.OrderDbRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderDbRepository orderDbRepository;

    public OrderRepositoryImpl(final OrderDbRepository orderDbRepository) {
        this.orderDbRepository = orderDbRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderDbRepository.save(OrderMapper.convertOrderToOrderEntity(order));
        return OrderMapper.convertOrderEntityToOrder(orderEntity);
    }

    @Override
    public Order findByExternalId(String externalId) {
        Optional<OrderEntity> orderEntity = orderDbRepository.findByExternalId(externalId);
        return orderEntity.map(OrderMapper::convertOrderEntityToOrder).orElse(null);
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<OrderEntity> entity = orderDbRepository.findById(id);
        return entity.map(OrderMapper::convertOrderEntityToOrder).or(Optional::empty);
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders = new java.util.ArrayList<>(List.of());
        List<OrderEntity> orderEntities = orderDbRepository.findAll();
        orderEntities.forEach(orderEntity -> {
            orders.add(OrderMapper.convertOrderEntityToOrder(orderEntity));
        });
        return orders;
    }


}
