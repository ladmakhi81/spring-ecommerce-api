package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.common.SendEmailHtmlMessage;
import com.codyproo.ecommerce.dtos.common.SendEmailTextMessage;


public interface EmailService {
    void sendTextMessage(SendEmailTextMessage dto);

    void sendHtmlMessage(SendEmailHtmlMessage dto) throws Exception;
}
