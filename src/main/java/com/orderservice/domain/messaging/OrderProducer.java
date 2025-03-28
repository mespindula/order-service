package com.orderservice.domain.messaging;

import com.orderservice.domain.data.model.event.OrderProducerPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProducer {
    private final KafkaTemplate<String, OrderProducerPayload> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderProducerPayload> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProcessedOrder(OrderProducerPayload payload) {
        log.info("🚀 Enviando mensagem para Kafka: {}", payload);
        kafkaTemplate.send("processed-orders", payload.getExternalId(), payload);
    }
}
