package com.codyproo.ecommerce.controllers;

import com.codyproo.ecommerce.dtos.cart.AddCartItemDto;
import com.codyproo.ecommerce.dtos.cart.CartDto;
import com.codyproo.ecommerce.dtos.common.SecuredUserDetailsDto;
import com.codyproo.ecommerce.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            @AuthenticationPrincipal SecuredUserDetailsDto loggedInUser
    ) throws Exception {
        var response = cartService.createCart(loggedInUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/item")
    public ResponseEntity<?> addItemToCart(
            @AuthenticationPrincipal SecuredUserDetailsDto loggedInUser,
            @Valid @RequestBody AddCartItemDto dto
    ) throws Exception {
        cartService.addItemToCart(dto, loggedInUser.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("detail")
    public ResponseEntity<CartDto> getCart(
            @AuthenticationPrincipal SecuredUserDetailsDto loggedInUser
    ) throws Exception {
        var response = cartService.getItems(loggedInUser.getId());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("items/{productId}")
    public ResponseEntity<?> deleteItemFromCart(
            @PathVariable("productId") Long productId,
            @AuthenticationPrincipal SecuredUserDetailsDto loggedInUser
    ) throws Exception {
        cartService.deleteItemFromCart(productId, loggedInUser.getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal SecuredUserDetailsDto loggedInUser) throws Exception {
        cartService.clearCartByUserId(loggedInUser.getId());
        return ResponseEntity.noContent().build();
    }
}
