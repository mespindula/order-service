package com.orderservice.domain.messaging;

import com.orderservice.domain.data.model.event.OrderConsumerPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Slf4j
public class OrderConsumerPayloadDeserializer implements Deserializer<OrderConsumerPayload> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderConsumerPayloadDeserializer() {
        log.info("✅ DESERIALIZADOR FOI INICIALIZADO!");
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public OrderConsumerPayload deserialize(String topic, byte[] data) {
        try {
            log.info("🚀 Deserializando payload: " + new String(data));
            return objectMapper.readValue(data, OrderConsumerPayload.class);
        } catch (Exception e) {
            log.error("Falha ao deserializar mensagem: ", e);
            throw new RuntimeException("Falha ao deserializar a mensagem do Kafka", e);
        }
    }

    @Override
    public void close() {
    }
}
