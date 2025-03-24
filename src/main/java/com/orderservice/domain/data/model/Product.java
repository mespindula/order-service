package com.orderservice.domain.data.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private String name;

    private BigDecimal unitPrice;
}
