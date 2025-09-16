package com.codyproo.ecommerce.dtos.auth;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
