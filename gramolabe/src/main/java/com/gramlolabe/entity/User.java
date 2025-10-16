package com.gramlolabe.entity;

import jakarta.persistence.*;

@Entity
public class User {
    private String bar;
    private String pwd1;
    private String pwd2;
    private String email;
    @Id private String clientID;
    private String clientSecret;


    
    public User(String bar, String pwd1, String pwd2, String email, String clientID, String clientSecret) {
        this.bar = bar;
        this.pwd1 = pwd1;
        this.pwd2 = pwd2;
        this.email = email;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }
    
    public String getBar() {
        return bar;
    }
    public void setBar(String bar) {
        this.bar = bar;
    }
    public String getPwd1() {
        return pwd1;
    }
    public void setPwd1(String pwd1) {
        this.pwd1 = pwd1;
    }
    public String getPwd2() {
        return pwd2;
    }
    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getClientID() {
        return clientID;
    }
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    
}
