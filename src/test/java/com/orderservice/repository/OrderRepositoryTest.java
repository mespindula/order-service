package com.orderservice.repository;

import com.orderservice.infrastructure.data.model.OrderEntity;
import com.orderservice.infrastructure.data.model.ProductEntity;
import com.orderservice.infrastructure.repository.persistence.db.OrderDbRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class OrderRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private OrderDbRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void shouldSaveAndRetrieveOrder() {
        ProductEntity product = ProductEntity.builder()
                .name("teste")
                .unitPrice(new BigDecimal("55.55"))
                .build();

        OrderEntity order = OrderEntity.builder()
                .externalId("12345")
                .totalValue(new BigDecimal("100.00"))
                .status("PROCESSADO")
                .products(List.of(product))
                .build();

        product.setOrder(order);

        orderRepository.save(order);
        OrderEntity savedOrder = orderRepository.findByExternalId("12345").orElse(null);

        assertNotNull(savedOrder);
        assertEquals("12345", savedOrder.getExternalId());
        assertEquals("PROCESSADO", savedOrder.getStatus());
    }

}
