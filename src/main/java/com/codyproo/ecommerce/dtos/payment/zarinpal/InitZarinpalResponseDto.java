package com.codyproo.ecommerce.dtos.payment.zarinpal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InitZarinpalResponseDto {
    @JsonProperty("data")
    private InitZarinpalDataDto data;
}
