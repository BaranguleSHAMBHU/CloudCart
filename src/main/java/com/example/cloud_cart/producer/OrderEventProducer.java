package com.example.cloud_cart.producer;

import com.example.cloud_cart.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private static final String TOPIC = "order-events";
    
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.getOrderId()), event);
        System.out.println("Published OrderCreatedEvent to Kafka for Order ID: " + event.getOrderId());
    }
}