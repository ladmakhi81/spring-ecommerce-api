package com.codyproo.ecommerce.dtos.product;

import com.codyproo.ecommerce.dtos.category.CategoryDto;
import com.codyproo.ecommerce.dtos.user.UserDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private CategoryDto category;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
