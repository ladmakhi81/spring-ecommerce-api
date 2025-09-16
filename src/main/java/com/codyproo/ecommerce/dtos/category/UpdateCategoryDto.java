package com.codyproo.ecommerce.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCategoryDto {
    @NotBlank(message = "name is required")
    @Size(min = 3, max = 100, message = "name must be between 3 and 100 character")
    private String name;
}
