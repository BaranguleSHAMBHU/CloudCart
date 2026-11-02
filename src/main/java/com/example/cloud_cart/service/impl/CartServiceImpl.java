package com.example.cloud_cart.service.impl;

import com.example.cloud_cart.dto.AddToCartDto;
import com.example.cloud_cart.dto.CartItemResponseDto;
import com.example.cloud_cart.dto.CartResponseDto;
import com.example.cloud_cart.entity.Cart;
import com.example.cloud_cart.entity.CartItem;
import com.example.cloud_cart.entity.Product;
import com.example.cloud_cart.entity.User;
import com.example.cloud_cart.repository.CartRepository;
import com.example.cloud_cart.repository.ProductRepository;
import com.example.cloud_cart.repository.UserRepository;
import com.example.cloud_cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public CartResponseDto addToCart(String email, AddToCartDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getAvailableQuantity() < dto.getQuantity()) {
            throw new RuntimeException("Not enough stock available");
        }

        Cart cart = cartRepository.findByUserId(user.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(user.getUserId());
                    return newCart;
                });

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(product.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + dto.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductId(product.getProductId());
            newItem.setQuantity(dto.getQuantity());
            cart.getItems().add(newItem);
        }

        Cart savedCart = cartRepository.save(cart);
        return mapToResponseDto(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDto getCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        return mapToResponseDto(cart);
    }

    private CartResponseDto mapToResponseDto(Cart cart) {
        CartResponseDto response = new CartResponseDto();
        response.setCartId(cart.getCartId());
        response.setUserId(cart.getUserId());

        List<CartItemResponseDto> itemDtos = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product no longer exists"));

            CartItemResponseDto itemDto = new CartItemResponseDto();
            itemDto.setCartItemId(item.getCartItemId());
            itemDto.setProductId(product.getProductId());
            itemDto.setProductName(product.getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(product.getPrice());

            BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            itemDto.setSubTotal(subTotal);

            itemDtos.add(itemDto);
            totalAmount = totalAmount.add(subTotal);
        }

        response.setItems(itemDtos);
        response.setTotalAmount(totalAmount);
        return response;
    }
}