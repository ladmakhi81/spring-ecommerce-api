package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.payment.CreatePaymentDto;
import com.codyproo.ecommerce.entities.Payment;

public interface PaymentService {
    Payment createPayment(CreatePaymentDto dto) throws Exception;

    void verifyPayment(String authority, String status) throws Exception;
}
