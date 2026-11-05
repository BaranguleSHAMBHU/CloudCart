package com.example.cloud_cart.service;


import com.example.cloud_cart.dto.OrderResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(String email);

    List<OrderResponseDto> getUserOrders(String email);
}