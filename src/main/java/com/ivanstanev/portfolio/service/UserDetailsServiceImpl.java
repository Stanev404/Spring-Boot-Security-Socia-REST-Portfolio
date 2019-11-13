package com.ivanstanev.portfolio.service;

import java.util.ArrayList;
import java.util.List;


import com.ivanstanev.portfolio.dao.AppRoleDAO;
import com.ivanstanev.portfolio.dao.AppUserDAO;
import com.ivanstanev.portfolio.entity.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;



    @Override
    public UserDetails loadUserByUsername(String email) {
        System.out.println("in login1");

        AppUser appUser = this.appUserDAO.findUserAccountByEmail(email);

        if (appUser == null) {
            System.out.println("User not found! " + email);
            throw new RuntimeException("User " + email + " was not found in the database");
        }

        if(appUser.getEnabled() == 0){
            System.out.println("User is not activated! " + email);
            throw new RuntimeException(" User " + email + " is not activated.");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getEmail(), //
                appUser.getEncrytedPassword(), grantList);

        return userDetails;
    }

}