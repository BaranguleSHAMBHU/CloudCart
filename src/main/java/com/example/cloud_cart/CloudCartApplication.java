package com.example.cloud_cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

import io.github.cdimascio.dotenv.Dotenv;

@EnableCaching
@EnableKafka
@SpringBootApplication(scanBasePackages = "com.example.cloud_cart")
public class CloudCartApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(
                        entry.getKey(),
                        entry.getValue()
                )
        );

        SpringApplication.run(CloudCartApplication.class, args);
    }
}