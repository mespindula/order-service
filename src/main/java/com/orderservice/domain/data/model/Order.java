package com.orderservice.domain.data.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private Long id;

    private String externalId;

    private BigDecimal totalValue;

    private String status;

    private List<Product> products;
}
