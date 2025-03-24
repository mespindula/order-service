package com.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orderservice.domain.data.model.Order;
import com.orderservice.domain.data.model.Product;
import com.orderservice.domain.data.model.event.OrderConsumerPayload;
import com.orderservice.domain.messaging.OrderProducer;
import com.orderservice.domain.service.OrderService;
import com.orderservice.infrastructure.repository.persistence.OrderRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepositoryImpl orderRepository;

    @Mock
    private OrderProducer producer;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProcessOrderSuccessfully() throws JsonProcessingException {

        OrderConsumerPayload consumerPayload = buildOrderConsumerPayload();

        when(orderRepository.findByExternalId(consumerPayload.getExternalId())).thenReturn(null);

        orderService.processOrder(consumerPayload);

        verify(producer).sendProcessedOrder(any());
    }

    @Test
    void shouldNotProcessDuplicateOrder() {

        OrderConsumerPayload consumerPayload = buildOrderConsumerPayload();

        when(orderRepository.findByExternalId(consumerPayload.getExternalId())).thenReturn(Order.builder().externalId("teste_12345").build());

        orderService.processOrder(consumerPayload);

        verify(orderRepository, never()).save(any(Order.class));
    }

    private OrderConsumerPayload buildOrderConsumerPayload() {
        Product product1 = Product.builder()
                .name("teste_1")
                .unitPrice(new BigDecimal("100.99"))
                .build();

        Product product2 = Product.builder()
                .name("teste_2")
                .unitPrice(new BigDecimal("899.01"))
                .build();

        List<Product> products = List.of(product1, product2);

        return OrderConsumerPayload.builder()
                .externalId("teste_12345")
                .products(products)
                .build();
    }
}
