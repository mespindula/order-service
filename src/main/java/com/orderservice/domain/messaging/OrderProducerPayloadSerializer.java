package com.orderservice.domain.messaging;

import com.orderservice.domain.data.model.event.OrderProducerPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OrderProducerPayloadSerializer implements Serializer<OrderProducerPayload> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderProducerPayloadSerializer() {
        System.out.println("✅ SERIALIZADOR FOI INICIALIZADO!");
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, OrderProducerPayload data) {
        try {
            System.out.println("🚀 Serializando payload: " + data);
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar a mensagem para Kafka", e);
        }
    }

    @Override
    public void close() {
    }
}
