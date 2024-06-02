package com.LoginRegisterAuth.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    private String fullName;
    private String mobile;

    @Id
    private String email;

    private String username;
    private String password;

    private boolean isOtpVerified = false;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOtpVerified() {
        return isOtpVerified;
    }

    public void setOtpVerified(boolean isOtpVerified) {
        this.isOtpVerified = isOtpVerified;
    }
}

