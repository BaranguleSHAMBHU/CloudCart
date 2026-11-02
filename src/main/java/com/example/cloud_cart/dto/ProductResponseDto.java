package com.example.cloud_cart.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private String categoryName; // Flattened from Category entity
    private String imageUrl;
}