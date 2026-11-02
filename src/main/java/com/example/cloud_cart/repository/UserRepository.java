package com.example.cloud_cart.repository;

import com.example.cloud_cart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query method to find a user by their email during login
    Optional<User> findByEmail(String email);
}