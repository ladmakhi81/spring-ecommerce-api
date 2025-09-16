package com.codyproo.ecommerce.listeners;

import com.codyproo.ecommerce.dtos.common.SendEmailHtmlMessage;
import com.codyproo.ecommerce.events.PurchaseOrderEvent;
import com.codyproo.ecommerce.services.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EmailListener {
    private final EmailService emailService;

    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void sendEmail(PurchaseOrderEvent dto) throws Exception {
        var param = new HashMap<String, Object>();
        param.put("subject", dto.getSubject());
        param.put("id", dto.getId());
        param.put("fullName", dto.getFullName());
        emailService.sendHtmlMessage(
                SendEmailHtmlMessage.builder()
                        .parameters(param)
                        .subject("Your Bucket Saved Successfully. Thank you for your shopping")
                        .to(dto.getEmail())
                        .template("ApprovedShopping")
                        .build()
        );
    }
}
