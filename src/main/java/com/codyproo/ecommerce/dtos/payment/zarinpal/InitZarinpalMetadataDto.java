package com.codyproo.ecommerce.dtos.payment.zarinpal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InitZarinpalMetadataDto {
    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("email")
    private String email;
}
