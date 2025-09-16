package com.codyproo.ecommerce.dtos.payment.zarinpal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VerifyZarinpalResponseDataDto {
    @JsonProperty("code")
    private Long code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("card_hash")
    private String cardHash;

    @JsonProperty("card_pan")
    private String cardPan;

    @JsonProperty("ref_id")
    private Long refId;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("fee")
    private Long fee;
}
