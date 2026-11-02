package com.example.cloud_cart.dto;

import lombok.Data;

@Data
public class AddToCartDto {
    private Long productId;
    private Integer quantity;
}