package com.example.cloud_cart.controller;

import com.example.cloud_cart.dto.ProductRequestDto;
import com.example.cloud_cart.dto.ProductResponseDto;
import com.example.cloud_cart.entity.Product;
import com.example.cloud_cart.repository.ProductRepository;
import com.example.cloud_cart.service.ProductService;
import com.example.cloud_cart.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
        return new ResponseEntity<>(productService.createProduct(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productId") String sortBy) {
        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy));
    }

    @PostMapping("/{productId}/image")
    public ResponseEntity<String> uploadProductImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            String imageUrl = s3Service.uploadImage(file);

            product.setImageUrl(imageUrl);
            productRepository.save(product);

            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload image");
        }
    }
}