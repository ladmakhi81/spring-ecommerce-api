package com.codyproo.ecommerce.dtos.payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyRequestResponseDto {
    private String cardHash;
    private String cardPan;
    private Long refId;
    private String message;
    private Long code;
}
