package com.codyproo.ecommerce.dtos.cart;

import com.codyproo.ecommerce.dtos.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
    private List<CartItemDto> cartItems;
}
