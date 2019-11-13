package com.ivanstanev.portfolio.entity.email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Email {
    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String subject;

    @NotNull
    @Size(min = 10)
    private String message;


    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Email{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
