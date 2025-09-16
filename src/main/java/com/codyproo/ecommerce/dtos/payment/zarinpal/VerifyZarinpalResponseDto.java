package com.codyproo.ecommerce.dtos.payment.zarinpal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VerifyZarinpalResponseDto {
    @JsonProperty("data")
    private VerifyZarinpalResponseDataDto data;
}
