package com.example.cloud_cart.service;

import com.example.cloud_cart.dto.ProductRequestDto;
import com.example.cloud_cart.dto.ProductResponseDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);

    Page<ProductResponseDto> getAllProducts(int page, int size, String sortBy);
}