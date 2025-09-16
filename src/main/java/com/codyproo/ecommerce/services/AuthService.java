package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.auth.AuthResponseDto;
import com.codyproo.ecommerce.dtos.user.CreateBasicUserDto;
import com.codyproo.ecommerce.dtos.auth.LoginDto;

public interface AuthService {
    AuthResponseDto login(LoginDto dto) throws Exception;

    AuthResponseDto signup(CreateBasicUserDto dto) throws Exception;

    AuthResponseDto refreshToken(String refreshToken) throws Exception;
}
