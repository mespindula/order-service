package com.orderservice.domain.service;

import com.orderservice.domain.data.model.Order;
import com.orderservice.domain.data.model.Product;
import com.orderservice.domain.data.model.event.OrderConsumerPayload;
import com.orderservice.domain.data.model.event.OrderProducerPayload;
import com.orderservice.domain.messaging.OrderProducer;
import com.orderservice.infrastructure.repository.persistence.OrderRepositoryImpl;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class OrderService {
    private final OrderRepositoryImpl orderRepository;
    private final OrderProducer producer;

    public OrderService(final OrderRepositoryImpl orderRepository, final OrderProducer producer) {
        this.orderRepository = orderRepository;
        this.producer = producer;
    }

    @Transactional
    public void processOrder(OrderConsumerPayload consumerPayload) {
        try {
            Order order = orderRepository.findByExternalId(consumerPayload.getExternalId());

            if (nonNull(order)) {
                log.info("Pedido já processado: {}", consumerPayload.getExternalId());
                return;
            }

            List<Product> products = consumerPayload.getProducts();
            BigDecimal total = products.stream()
                    .map(Product::getUnitPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            order = Order.builder()
                    .externalId(consumerPayload.getExternalId())
                    .status("PROCESSADO")
                    .totalValue(total)
                    .products(products)
                    .build();

            orderRepository.save(order);

            OrderProducerPayload producerPayload = OrderProducerPayload.builder()
                    .externalId(order.getExternalId())
                    .totalValue(order.getTotalValue())
                    .products(order.getProducts())
                    .build();

            producer.sendProcessedOrder(producerPayload);
            log.info("Pedido processado com sucesso: {}", order.getExternalId());
        } catch (OptimisticLockException e) {
            log.error("Conflito de concorrência ao salvar pedido: {}", consumerPayload.getExternalId(), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao processar pedido: {}", consumerPayload.getExternalId(), e);
        }
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }
}
