package com.orderservice.domain.messaging;

import com.orderservice.domain.data.model.event.OrderProducerPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Slf4j
public class OrderProducerPayloadSerializer implements Serializer<OrderProducerPayload> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderProducerPayloadSerializer() {
        log.info("✅ SERIALIZADOR FOI INICIALIZADO!");
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, OrderProducerPayload data) {
        try {
            log.info("🚀 Serializando payload: " + data);
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao serializar a mensagem para Kafka", e);
        }
    }

    @Override
    public void close() {
    }
}
