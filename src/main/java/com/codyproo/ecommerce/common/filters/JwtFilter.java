package com.codyproo.ecommerce.common.filters;

import com.codyproo.ecommerce.dtos.common.SecuredUserDetailsDto;
import com.codyproo.ecommerce.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        var token = authorization.replaceAll("Bearer ", "");
        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        var claims = jwtService.verifyAccessToken(token);
        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var authorities = ((List<?>) claims.get("roles")).stream()
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .toList();
        var userDetails = new SecuredUserDetailsDto(claims.getSubject(), claims.get("id", Long.class));
        var auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
