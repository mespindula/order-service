package com.orderservice.domain.messaging;

import com.orderservice.domain.data.model.event.OrderConsumerPayload;
import com.orderservice.domain.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {
    private final OrderService orderService;

    public OrderConsumer(final OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "new-orders", groupId = "order-group", concurrency = "3")
    public void consumeNewOrder(ConsumerRecord<String, OrderConsumerPayload> order) {
        try {
            System.out.println("📥 Mensagem recebida no Kafka!");
            System.out.println("Partição: " + order.partition() + " | Offset: " + order.offset());
            System.out.println("VALOR: " + order.value());
           // OrderConsumerPayload order = new ObjectMapper().readValue(record.value(), OrderConsumerPayload.class);
            System.out.println("Order deserializado: " + order.value());
            orderService.processOrder(order.value());
        } catch (Exception e) {
            System.out.println("ERRO: "+e.toString());
        }
    }

}
