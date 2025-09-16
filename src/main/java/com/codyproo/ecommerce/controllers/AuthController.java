package com.codyproo.ecommerce.controllers;

import com.codyproo.ecommerce.dtos.user.CreateBasicUserDto;
import com.codyproo.ecommerce.dtos.auth.LoginDto;
import com.codyproo.ecommerce.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody CreateBasicUserDto dto,
            HttpServletResponse response
    ) throws Exception {
        var result = authService.signup(dto);
        response.addCookie(createCookie(result.getRefreshToken()));
        return ResponseEntity.status(HttpStatus.CREATED).body(result.getAccessToken());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginDto dto,
            HttpServletResponse response
    ) throws Exception {
        var result = authService.login(dto);
        response.addCookie(createCookie(result.getRefreshToken()));
        return ResponseEntity.ok(result.getAccessToken());
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(
            @CookieValue(name = "x-refresh-token", required = false) String refreshToken
    ) throws Exception {
        var result = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(result.getAccessToken());
    }

    private Cookie createCookie(String refreshToken) {
        var cookie = new Cookie("x-refresh-token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh-token");
        cookie.setMaxAge(60 * 60 * 10);
        cookie.setSecure(true);
        return cookie;
    }
}
