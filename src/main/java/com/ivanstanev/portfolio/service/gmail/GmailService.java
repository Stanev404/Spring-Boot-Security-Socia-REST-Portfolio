package com.ivanstanev.portfolio.service.gmail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class GmailService implements IEmailService {

    private JavaMailSender javaMailSender;


    public GmailService(JavaMailSenderImpl sender) {

        Properties mailProperties = new Properties();

        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);
        sender.setJavaMailProperties(mailProperties);
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername("noreply.ivan.stanev@gmail.com");
        sender.setPassword("!Ivanstanev12");
        this.javaMailSender = sender;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String messageBody) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("noreply.ivan.stanev@gmail.com");
        message.setSubject(subject);
        message.setText(messageBody);
        this.javaMailSender.send(message);
    }

}