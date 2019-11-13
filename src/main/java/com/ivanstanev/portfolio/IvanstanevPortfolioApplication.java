package com.ivanstanev.portfolio;

import com.ivanstanev.portfolio.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;


@SpringBootApplication
public class IvanstanevPortfolioApplication {


    public static void main(String[] args) {


        SpringApplication.run(IvanstanevPortfolioApplication.class, args);
    }



}

