package com.ivanstanev.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

//    @GetMapping("/index")
//    public String index(){
//        return "index";
//    }


//    @GetMapping("/about_me")
//    public String aboutMe(){
//        return "about_me";
//    }

//    @GetMapping("/contacts")
//    public String contacts(){
//        return "contacts";
//    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "registration";
    }

//    @GetMapping("/userInfo")
//    public String userInfo(){
//        return "userInfo";
//    }



}
