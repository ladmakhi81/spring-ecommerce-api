package com.codyproo.ecommerce.dtos.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailTextMessage {
    private String to;
    private String subject;
    private String text;
}