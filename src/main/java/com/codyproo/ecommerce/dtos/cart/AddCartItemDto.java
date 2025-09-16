package com.codyproo.ecommerce.dtos.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCartItemDto {
    @NotBlank(message = "product is required")
    @NotNull(message = "product is required")
    @Min(value = 1, message = "product contain invalid format")
    private Long productId;
}
