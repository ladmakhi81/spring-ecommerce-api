package com.codyproo.ecommerce.dtos.common;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SendEmailHtmlMessage {
    private String to;
    private String subject;
    private String template;
    private Map<String, Object> parameters;
}
