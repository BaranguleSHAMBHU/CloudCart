package com.example.cloud_cart.repository;

import com.example.cloud_cart.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Automatically generates SQL: SELECT * FROM products WHERE LOWER(name) LIKE
    // LOWER('%?%')
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Automatically generates SQL: SELECT * FROM products WHERE category_id = ?
    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);
}