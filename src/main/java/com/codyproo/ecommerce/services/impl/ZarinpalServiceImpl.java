package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.dtos.payment.*;
import com.codyproo.ecommerce.dtos.payment.zarinpal.*;
import com.codyproo.ecommerce.services.HttpClient;
import com.codyproo.ecommerce.services.PaymentGatewayService;
import org.springframework.stereotype.Service;

@Service
public class ZarinpalServiceImpl implements PaymentGatewayService {
    private final ZarinpalConfigDto zarinpalConfigDto;
    private final HttpClient httpClient;

    public ZarinpalServiceImpl(ZarinpalConfigDto zarinpalConfigDto, HttpClient httpClient) {
        this.zarinpalConfigDto = zarinpalConfigDto;
        this.httpClient = httpClient;
    }

    @Override
    public InitRequestResponseDto initRequest(InitRequestDto dto) throws Exception {
        var requestDto = InitZarinpalDto.builder()
                .amount(dto.getAmount())
                .callbackUrl(zarinpalConfigDto.getCallbackUrl())
                .description("zarinpal gateway")
                .merchantId(zarinpalConfigDto.getMerchantId())
                .metaData(
                        InitZarinpalMetadataDto.builder()
                                .email(zarinpalConfigDto.getEmailSupporter())
                                .mobile(zarinpalConfigDto.getMobileSupporter())
                                .build()
                )
                .build();
        var response = httpClient.post(zarinpalConfigDto.getInitRequest(), requestDto, InitZarinpalResponseDto.class);
        if (response == null || response.getData() == null) {
            return null;
        }
        return new InitRequestResponseDto(response.getData().getAuthority());
    }

    @Override
    public VerifyRequestResponseDto verifyRequest(VerifyRequestDto dto) throws Exception {
        var requestDto = VerifyZarinpalDto.builder()
                .amount(dto.getAmount())
                .merchantId(zarinpalConfigDto.getMerchantId())
                .authority(dto.getAuthority())
                .build();
        var response = httpClient.post(zarinpalConfigDto.getVerifyRequest(), requestDto, VerifyZarinpalResponseDto.class);
        if (response == null || response.getData() == null) {
            return null;
        }
        var data = response.getData();
        return VerifyRequestResponseDto.builder()
                .refId(data.getRefId())
                .cardPan(data.getCardPan())
                .message(data.getMessage())
                .cardHash(data.getCardHash())
                .code(data.getCode())
                .build();
    }
}
