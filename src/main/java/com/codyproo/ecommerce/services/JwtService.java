package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.common.SecuredUserDetailsDto;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateAccessToken(SecuredUserDetailsDto user);

    String generateRefreshToken(SecuredUserDetailsDto user);

    Claims verifyAccessToken(String accessToken);
}
