package com.ivanstanev.portfolio.entity.auth;

public class AppUser {

    private Long userId;
    private String email;
    private String name;
    private String encrytedPassword;
    private int enabled;

    public AppUser() {

    }

    public AppUser(Long userId, String name, String email, String encrytedPassword, int enabled) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return this.email + "/" + this.encrytedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}