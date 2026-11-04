package com.example.cloud_cart.service.impl;

import com.example.cloud_cart.dto.ProductRequestDto;
import com.example.cloud_cart.dto.ProductResponseDto;
import com.example.cloud_cart.entity.Category;
import com.example.cloud_cart.entity.Product;
import com.example.cloud_cart.repository.CategoryRepository;
import com.example.cloud_cart.repository.ProductRepository;
import com.example.cloud_cart.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return mapToResponseDto(savedProduct);
    }

    @Override
    @Cacheable(value = "products", key = "#page + '-' + #size + '-' + #sortBy")
    public Page<ProductResponseDto> getAllProducts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::mapToResponseDto);
    }

    private ProductResponseDto mapToResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAvailableQuantity(product.getAvailableQuantity());
        dto.setCategoryName(product.getCategory().getCategoryName());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }
}