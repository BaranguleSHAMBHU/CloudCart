
package com.example.cloud_cart.consumer;

import com.example.cloud_cart.event.OrderCreatedEvent;
import com.example.cloud_cart.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final EmailService emailService;

    public OrderEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
        topics = "order-events",
        groupId = "cloudcart-group"
    )
    public void consumeOrderEvent(OrderCreatedEvent event) {

        System.out.println("==============================================");
        System.out.println("KAFKA CONSUMER TRIGGERED");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("User Email: " + event.getUserEmail());
        System.out.println("Total Amount: " + event.getTotalAmount());

        try {
            emailService.sendOrderConfirmationEmail(
                event.getUserEmail(),
                event.getOrderId(),
                event.getTotalAmount()
            );

            System.out.println("EMAIL SERVICE CALL COMPLETED");

        } catch (Exception e) {
            System.err.println("EMAIL SENDING FAILED");
            e.printStackTrace();
        }

        System.out.println("==============================================");
    }
}
