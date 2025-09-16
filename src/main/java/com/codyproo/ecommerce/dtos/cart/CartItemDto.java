package com.codyproo.ecommerce.dtos.cart;

import com.codyproo.ecommerce.dtos.product.ProductDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartItemDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductDto product;
}
