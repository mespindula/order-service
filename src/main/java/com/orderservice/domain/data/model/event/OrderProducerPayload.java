package com.orderservice.domain.data.model.event;

import com.orderservice.domain.data.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProducerPayload {

    private String externalId;
    private BigDecimal totalValue;
    private List<Product> products;

}
