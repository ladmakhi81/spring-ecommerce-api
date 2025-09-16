package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.common.exceptions.BadRequestException;
import com.codyproo.ecommerce.dtos.auth.AuthResponseDto;
import com.codyproo.ecommerce.dtos.common.SecuredUserDetailsDto;
import com.codyproo.ecommerce.dtos.user.CreateBasicUserDto;
import com.codyproo.ecommerce.dtos.auth.LoginDto;
import com.codyproo.ecommerce.services.AuthService;
import com.codyproo.ecommerce.services.JwtService;
import com.codyproo.ecommerce.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDto login(LoginDto dto) throws Exception {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );
        if (!auth.isAuthenticated()) {
            throw new BadRequestException("invalid authentication credentials");
        }
        return _generateAuthToken((SecuredUserDetailsDto) auth.getPrincipal());
    }

    @Override
    public AuthResponseDto signup(CreateBasicUserDto dto) throws Exception {
        var user = userService.createBasicUser(dto);
        var auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return _generateAuthToken(new SecuredUserDetailsDto(user));
    }

    @Override
    public AuthResponseDto refreshToken(String refreshToken) throws Exception {
        var claims = jwtService.verifyAccessToken(refreshToken);
        if (claims == null) {
            throw new BadRequestException("invalid refresh token");
        }
        var user = userService.getUserByEmail(claims.getSubject());
        return _generateAuthToken(new SecuredUserDetailsDto(user));
    }

    private AuthResponseDto _generateAuthToken(SecuredUserDetailsDto principal) {
        var accessToken = jwtService.generateAccessToken(principal);
        var refreshToken = jwtService.generateRefreshToken(principal);
        return new AuthResponseDto(accessToken, refreshToken);
    }
}
