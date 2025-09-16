package com.codyproo.ecommerce.dtos.payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.zarinpal")
@Getter
@Setter
public class ZarinpalConfigDto {
    private String initRequest;
    private String callbackUrl;
    private String verifyRequest;
    private String merchantId;
    private String emailSupporter;
    private String mobileSupporter;
    private String payLink;
}
