package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.payment.InitRequestDto;
import com.codyproo.ecommerce.dtos.payment.InitRequestResponseDto;
import com.codyproo.ecommerce.dtos.payment.VerifyRequestDto;
import com.codyproo.ecommerce.dtos.payment.VerifyRequestResponseDto;

public interface PaymentGatewayService {
    InitRequestResponseDto initRequest(InitRequestDto dto) throws Exception;

    VerifyRequestResponseDto verifyRequest(VerifyRequestDto dto) throws Exception;
}
