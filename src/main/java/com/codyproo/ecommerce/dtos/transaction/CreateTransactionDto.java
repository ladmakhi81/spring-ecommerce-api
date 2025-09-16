package com.codyproo.ecommerce.dtos.transaction;

import com.codyproo.ecommerce.dtos.payment.VerifyRequestResponseDto;
import com.codyproo.ecommerce.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTransactionDto {
    private VerifyRequestResponseDto verificationResult;
    private Payment payment;
}
