package com.ivanstanev.portfolio.service.gmail;

import org.springframework.stereotype.Component;

@Component
public interface IEmailService {
    void sendEmail(String toEmail, String subject, String messageBody);
}