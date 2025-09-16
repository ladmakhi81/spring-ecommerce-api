package com.codyproo.ecommerce.dtos.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SubmitOrderDto {
    @NotBlank(message = "cart item ids required")
    @NotNull(message = "cart item ids required")
    @Size(min = 1, message = "cart item ids must at least contain single id")
    private List<@NotNull @Positive Long> cartItemIds;
}
