package com.ivanstanev.portfolio.controller;

import com.ivanstanev.portfolio.config.utils.WebUtils;
import com.ivanstanev.portfolio.dao.AppUserDAO;
import com.ivanstanev.portfolio.dao.VerificationTokenDAO;
import com.ivanstanev.portfolio.entity.VerificationToken;
import com.ivanstanev.portfolio.entity.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthenticationViewController {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model, Principal principal) {
        String userName;
// check if connection is from facebook
        if(principal.getName().matches("[0-9]+")) {
            Map<String, String> facebookMap = getFacebookNameAndEmailMap((OAuth2Authentication) principal);
            String facebookEmail = facebookMap.get("email");
            String facebookName = facebookMap.get("name");
            if(this.appUserDAO.findUserAccountByEmail(facebookEmail) == null){
                this.appUserDAO.saveFacebookAccount(facebookMap);
            }
            userName = facebookName;

        }
        else{
            String email = principal.getName();

            AppUser loggedUser = this.appUserDAO.findUserAccountByEmail(email);
            userName = loggedUser.getName();
            System.out.println("Normal login: User Name: " + userName);
        }

        model.addAttribute("userName", userName);

        return "index";
    }

    private Map<String, String> getFacebookNameAndEmailMap(OAuth2Authentication principal) {
        Map<String, Object> details = (Map<String, Object>) principal.getUserAuthentication().getDetails();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", (String) details.get("name"));
        map.put("email", (String) details.get("email"));
        return map;
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration (Model model, @RequestParam("token") String token) {
        System.out.println(token);
        VerificationToken currentVerificationToken = this.verificationTokenDAO.validateToken(token);
        if(currentVerificationToken != null) {
            Long userIdToBeEnabled = currentVerificationToken.getUserId();
            this.appUserDAO.setEnabledToTrue(userIdToBeEnabled);
            this.verificationTokenDAO.deleteToken(currentVerificationToken.getTokenId());
        }
        model.addAttribute("activationMessage", "Account activated");
        return "redirect:/login?verified=true";

    }



    @RequestMapping(value = {"/about_me"}, method = RequestMethod.GET)
    public String aboutMe(Model model, Principal principal) {
        // check if connection is from facebook
        if(principal.getName().matches("[0-9]+")) {
            Map<String, String> facebookMap = getFacebookNameAndEmailMap((OAuth2Authentication) principal);
            String facebookEmail = facebookMap.get("email");
            String facebookName = facebookMap.get("name");
            if(this.appUserDAO.findUserAccountByEmail(facebookEmail) == null){
                this.appUserDAO.saveFacebookAccount(facebookMap);
            }
            model.addAttribute("userName",facebookName);
        }
        else {
            bindUserName(model, principal);
        }
        return "about_me";
    }

    private void bindUserName(Model model, Principal principal) {
        String email = principal.getName();
        AppUser loggedUser = this.appUserDAO.findUserAccountByEmail(email);
        String userName = loggedUser.getName();

        model.addAttribute("userName", userName);
    }

    @RequestMapping(value = {"/contacts"}, method = RequestMethod.GET)
    public String contacts(Model model, Principal principal) {
        // check if connection is from facebook
        if(principal.getName().matches("[0-9]+")) {
            Map<String, String> facebookMap = getFacebookNameAndEmailMap((OAuth2Authentication) principal);
            String facebookEmail = facebookMap.get("email");
            String facebookName = facebookMap.get("name");
            if (this.appUserDAO.findUserAccountByEmail(facebookEmail) == null) {
                this.appUserDAO.saveFacebookAccount(facebookMap);
            }
            model.addAttribute("userName",facebookName);
            model.addAttribute("email",facebookEmail);
        }
        else {
            // this is how you set authentication is userdetailsservice - email and password
            String email = principal.getName();
            this.bindUserName(model, principal);
            model.addAttribute("email", email);
        }
        return "contacts";
    }

    @RequestMapping(value = {"/registerUser"}, method = RequestMethod.POST)
    public ResponseEntity<AppUser> registerUserInDB(@RequestBody AppUser jsonUser) {
        AppUser checkIfExists = this.appUserDAO.findUserAccountByEmail(jsonUser.getEmail());
        if (checkIfExists == null) {
            AppUser newUser = new AppUser();
            newUser.setEmail(jsonUser.getEmail());
            newUser.setName(jsonUser.getName());
            // Not ecnrypted pass
            newUser.setEncrytedPassword(jsonUser.getEncrytedPassword());
            newUser.setEnabled(0);
            this.appUserDAO.createUserAccount(newUser);
            return new ResponseEntity<>(checkIfExists, HttpStatus.CREATED);
        } else {
            System.out.println(checkIfExists);

//     TO DO: set the role of the new user
//        String userName= loggedUser.getName();
//
//        model.addAttribute("userName", userName);
//        model.addAttribute("email", email);

            return new ResponseEntity<>(HttpStatus.CONFLICT);

        }
    }


    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // (1) (en)
        // After user login successfully.
        // (vi)
        // Sau khi user login thanh cong se co principal
        String email = principal.getName();

        System.out.println("Email: " + email);

        User loggedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loggedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfo";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loggedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loggedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }


}
