package com.codyproo.ecommerce.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBasicUserDto {
    @NotBlank(message = "email is required")
    @NotNull(message = "email is required")
    @Email(message = "email has invalid format")
    private String email;

    @NotBlank(message = "password is required")
    @NotNull(message = "password is required")
    @Size(min = 8, message = "password should include at least 8 character")
    private String password;
}
