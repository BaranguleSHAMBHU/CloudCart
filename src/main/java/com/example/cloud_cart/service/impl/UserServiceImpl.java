package com.example.cloud_cart.service.impl;

import com.example.cloud_cart.dto.UserRegistrationDto;
import com.example.cloud_cart.entity.Role;
import com.example.cloud_cart.entity.User;
import com.example.cloud_cart.repository.UserRepository;
import com.example.cloud_cart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // New import
import org.springframework.stereotype.Service;
import com.example.cloud_cart.dto.UserLoginDto;
import com.example.cloud_cart.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inject the encoder
    private final JwtUtil jwtUtil;

    @Override
    public User registerUser(UserRegistrationDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(Role.CUSTOMER);

        return userRepository.save(user);
    }

    @Override
    public String loginUser(UserLoginDto dto) {
        // 1. Find user by email
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Verify password matches the BCrypt hash in the database
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3. Generate and return the JWT
        return jwtUtil.generateToken(user.getEmail());
    }
}