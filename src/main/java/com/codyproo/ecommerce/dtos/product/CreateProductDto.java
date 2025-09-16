package com.codyproo.ecommerce.dtos.product;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductDto {
    @NotBlank(message = "name is required")
    @NotNull(message = "name is required")
    @Size(min = 3, message = "name must at least include 3 character")
    private String name;

    @NotBlank(message = "description is required")
    @NotNull(message = "description is required")
    @Size(min = 10, message = "description must at least include 10 character")
    private String description;

    @NotBlank(message = "image is required")
    @NotNull(message = "image is required")
    private String image;

    @NotBlank(message = "price is required")
    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", message = "price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "category is required")
    @NotNull(message = "category is required")
    @Min(value = 1, message = "category is required")
    private Long categoryId;
}
