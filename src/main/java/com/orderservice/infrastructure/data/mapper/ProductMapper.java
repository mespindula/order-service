package com.orderservice.infrastructure.data.mapper;

import com.orderservice.domain.data.model.Product;
import com.orderservice.infrastructure.data.model.ProductEntity;

public class ProductMapper {

    public static Product convertProductEntityToProduct(ProductEntity entity) {
        return Product.builder()
                .name(entity.getName())
                .unitPrice(entity.getUnitPrice())
                .build();

    }

    public static ProductEntity convertProductEntityToProduct(Product product) {
        return ProductEntity.builder()
                .name(product.getName())
                .unitPrice(product.getUnitPrice())
                .build();
    }
}
