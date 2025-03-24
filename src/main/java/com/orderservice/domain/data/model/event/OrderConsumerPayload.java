package com.orderservice.domain.data.model.event;

import com.orderservice.domain.data.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConsumerPayload {

    private String externalId;
    private List<Product> products;
}
