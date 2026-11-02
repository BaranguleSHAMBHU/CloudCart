package com.example.cloud_cart.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private Long categoryId;
    private String imageUrl;
}