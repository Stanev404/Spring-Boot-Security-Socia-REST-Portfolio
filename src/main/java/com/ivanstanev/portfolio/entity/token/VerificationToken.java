package com.ivanstanev.portfolio.entity;

import com.ivanstanev.portfolio.entity.auth.AppUser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

public class VerificationToken {

    private Long tokenId;

    private String token;

    private Long userId;



    public VerificationToken() {
    }

    public VerificationToken(Long tokenId, String token, Long userId) {
        this.tokenId = tokenId;
        this.token = token;
        this.userId = userId;
    }


    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "tokenId=" + tokenId +
                ", token='" + token + '\'' +
                ", userId=" + userId +
                '}';
    }
}