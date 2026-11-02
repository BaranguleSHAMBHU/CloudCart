package com.example.cloud_cart.controller;

import com.example.cloud_cart.dto.AddToCartDto;
import com.example.cloud_cart.dto.CartResponseDto;
import com.example.cloud_cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(
            @RequestBody AddToCartDto addToCartDto,
            Authentication authentication) {
        CartResponseDto cartResponse = cartService.addToCart(authentication.getName(), addToCartDto);
        return ResponseEntity.ok(cartResponse);
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }
}