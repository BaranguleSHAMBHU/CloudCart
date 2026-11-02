package com.example.cloud_cart.service;

import com.example.cloud_cart.dto.UserLoginDto;
import com.example.cloud_cart.dto.UserRegistrationDto;
import com.example.cloud_cart.entity.User;

// Make sure it says "interface" here, not "class"
public interface UserService {
    User registerUser(UserRegistrationDto registrationDto);

    String loginUser(UserLoginDto loginDto);
}