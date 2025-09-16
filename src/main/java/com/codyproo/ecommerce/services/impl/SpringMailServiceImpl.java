package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.dtos.common.SendEmailHtmlMessage;
import com.codyproo.ecommerce.dtos.common.SendEmailTextMessage;
import com.codyproo.ecommerce.services.EmailService;
import com.codyproo.ecommerce.services.HtmlParser;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SpringMailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String mailSenderEmail;

    private final JavaMailSender javaMailSender;
    private final HtmlParser htmlParser;

    public SpringMailServiceImpl(JavaMailSender javaMailSender, HtmlParser htmlParser) {
        this.javaMailSender = javaMailSender;
        this.htmlParser = htmlParser;
    }

    @Override
    public void sendTextMessage(SendEmailTextMessage dto) {
        var message = new SimpleMailMessage();
        message.setFrom(mailSenderEmail);
        message.setTo(dto.getTo());
        message.setSubject(dto.getSubject());
        message.setText(dto.getText());
        message.setSentDate(new Date(System.currentTimeMillis()));
        javaMailSender.send(message);
    }

    @Override
    public void sendHtmlMessage(SendEmailHtmlMessage dto) throws Exception {
        var content = htmlParser.parseWithArgs(dto.getTemplate(), dto.getParameters());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mailSenderEmail);
        helper.setTo(dto.getTo());
        helper.setSubject(dto.getSubject());
        helper.setText(content, true);
        helper.setSentDate(new Date(System.currentTimeMillis()));
        javaMailSender.send(mimeMessage);
    }
}
