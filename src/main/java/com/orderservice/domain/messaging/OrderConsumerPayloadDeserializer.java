package com.orderservice.domain.messaging;

import com.orderservice.domain.data.model.event.OrderConsumerPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OrderConsumerPayloadDeserializer implements Deserializer<OrderConsumerPayload> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderConsumerPayloadDeserializer() {
        System.out.println("✅ DESERIALIZADOR FOI INICIALIZADO!");
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public OrderConsumerPayload deserialize(String topic, byte[] data) {
        try {
            System.out.println("🚀 Deserializando payload: " + new String(data));
            return objectMapper.readValue(data, OrderConsumerPayload.class);
        } catch (Exception e) {
            System.out.println("ERRO: "+e.toString());
            throw new RuntimeException("Erro ao deserializar a mensagem do Kafka", e);
        }
    }

    @Override
    public void close() {
    }
}
