package com.orderservice.infrastructure.data.mapper;

import com.orderservice.domain.data.model.Order;
import com.orderservice.domain.data.model.Product;
import com.orderservice.infrastructure.data.model.OrderEntity;
import com.orderservice.infrastructure.data.model.ProductEntity;

import java.util.List;

public class OrderMapper {

    public static Order convertOrderEntityToOrder(OrderEntity entity) {
        List<Product> products = new java.util.ArrayList<>(List.of());
        entity.getProducts().forEach(productEntity -> {
            products.add(ProductMapper.convertProductEntityToProduct(productEntity));
        });

        return Order.builder()
                .id(entity.getId())
                .externalId(entity.getExternalId())
                .totalValue(entity.getTotalValue())
                .status(entity.getStatus())
                .products(products)
                .build();
    }

    public static OrderEntity convertOrderToOrderEntity(Order order) {
        List<ProductEntity> productEntities = new java.util.ArrayList<>(List.of());
        order.getProducts().forEach(product -> {
            productEntities.add(ProductMapper.convertProductEntityToProduct(product));
        });

        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId())
                .externalId(order.getExternalId())
                .totalValue(order.getTotalValue())
                .status(order.getStatus())
                .products(productEntities)
                .build();

        productEntities.forEach(productEntity -> {
            productEntity.setOrder(orderEntity);
        });

        return orderEntity;
    }
}
