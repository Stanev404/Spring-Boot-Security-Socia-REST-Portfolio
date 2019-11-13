package com.ivanstanev.portfolio.controller;

import com.ivanstanev.portfolio.entity.email.Email;
import com.ivanstanev.portfolio.mail.config.EmailConfig;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private EmailConfig emailConfig;

    // this constructor performs autowired DIa
    public EmailController(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @PostMapping("/sendEmail")
    public Email sendFeedback(@RequestBody Email email) {
        // create mail sender

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        // create an email instance
        //System.out.println(email);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // email.getEmail()
        mailMessage.setFrom(email.getEmail());
        mailMessage.setTo("zirow000@gmail.com");
        // email.getSubject()
        mailMessage.setSubject(email.getSubject());
        // email.getMessage()
        mailMessage.setText(email.getMessage()  + System.lineSeparator() + "Name: " + email.getName() + System.lineSeparator() + "Phone: " + email.getPhone());

        // send mail

        mailSender.send(mailMessage);
        return email;
    }
}
