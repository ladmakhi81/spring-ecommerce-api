package com.codyproo.ecommerce.controllers;

import com.codyproo.ecommerce.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> verifyPayment(
            @RequestParam("Authority") String authority,
            @RequestParam("Status") String status
    ) throws Exception {
        System.out.println("Authority : " + authority);
        System.out.println("Status : " + status);
        paymentService.verifyPayment(authority, status);
        return ResponseEntity.noContent().build();
    }
}
