package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.dtos.common.SecuredUserDetailsDto;
import com.codyproo.ecommerce.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${app.auth.secret-key}")
    private String secretKey;

    public String generateAccessToken(SecuredUserDetailsDto user) {
        var expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // 1h
        return _generateGeneralToken(user, expiration);
    }

    public String generateRefreshToken(SecuredUserDetailsDto user) {
        var expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10); // 10h
        return _generateGeneralToken(user, expiration);
    }

    @Override
    public Claims verifyAccessToken(String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    private String _generateGeneralToken(SecuredUserDetailsDto user, Date expirationDate) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", List.of("ROLE_USER"));

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .signWith(getSecretKey())
                .subject(user.getUsername())
                .compact();
    }
}
