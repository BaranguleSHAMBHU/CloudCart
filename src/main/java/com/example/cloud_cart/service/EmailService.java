package com.example.cloud_cart.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // Explicit constructor instead of relying on @RequiredArgsConstructor
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderConfirmationEmail(String toEmail, Long orderId, BigDecimal totalAmount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mailSenderUsername");
        message.setTo(toEmail);
        message.setSubject("CloudCart Order Confirmation - Order #" + orderId);
        message.setText("Thank you for your purchase!\n\n" +
                "Your order #" + orderId + " has been successfully placed.\n" +
                "Total Amount: $" + totalAmount + "\n\n" +
                "We will notify you once it ships.");

        
try {
    System.out.println("Attempting to send email to: " + toEmail);

    mailSender.send(message);

    System.out.println("EMAIL SENT SUCCESSFULLY TO: " + toEmail);

} catch (Exception e) {
    System.err.println("EMAIL SENDING FAILED");
    e.printStackTrace();

    throw e;
}

        System.out.println("Actual email sent successfully to: " + toEmail);
    }
}