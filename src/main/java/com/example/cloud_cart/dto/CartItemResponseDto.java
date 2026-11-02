package com.example.cloud_cart.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemResponseDto {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
}