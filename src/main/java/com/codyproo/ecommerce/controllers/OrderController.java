package com.codyproo.ecommerce.controllers;

import com.codyproo.ecommerce.dtos.common.SecuredUserDetailsDto;
import com.codyproo.ecommerce.dtos.order.SubmitOrderDto;
import com.codyproo.ecommerce.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> SubmitOrder(
            @AuthenticationPrincipal SecuredUserDetailsDto loggedInUser,
            @Valid @RequestBody SubmitOrderDto dto
    ) throws Exception {
        var response = orderService.createOrder(loggedInUser.getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
