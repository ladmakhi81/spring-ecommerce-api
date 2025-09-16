package com.codyproo.ecommerce.dtos.payment.zarinpal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InitZarinpalDataDto {
    @JsonProperty("code")
    private Long code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("authority")
    private String authority;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("fee")
    private Long fee;
}
