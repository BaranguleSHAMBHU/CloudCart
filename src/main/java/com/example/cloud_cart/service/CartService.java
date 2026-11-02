package com.example.cloud_cart.service;

import com.example.cloud_cart.dto.AddToCartDto;
import com.example.cloud_cart.dto.CartResponseDto;

public interface CartService {
    CartResponseDto addToCart(String email, AddToCartDto dto);

    CartResponseDto getCart(String email);
}