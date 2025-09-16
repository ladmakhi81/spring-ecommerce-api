package com.codyproo.ecommerce.dtos.payment.zarinpal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class VerifyZarinpalDto {
    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("authority")
    private String authority;
}
