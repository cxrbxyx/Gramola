package com.gramlolabe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "bar", length = 255)
    private String bar;

    @Column(name = "client_id", length = 255)
    private String clientId;

    @Column(name = "client_secret", length = 255)
    private String clientSecret;

    @Column(name = "gramola_cookie", length = 255)
    private String gramolaCookie;

    @Column(name = "pwd", length = 255)
    private String pwd;

    @Column(name = "creation_token_id", length = 36)
    private String creationTokenId;

    public User() {}

    public User(String email, String bar, String clientId, String clientSecret, String gramolaCookie, String pwd, String creationTokenId) {
        this.email = email;
        this.bar = bar;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.gramolaCookie = gramolaCookie;
        this.pwd = pwd;
        this.creationTokenId = creationTokenId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGramolaCookie() {
        return gramolaCookie;
    }

    public void setGramolaCookie(String gramolaCookie) {
        this.gramolaCookie = gramolaCookie;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCreationTokenId() {
        return creationTokenId;
    }

    public void setCreationTokenId(String creationTokenId) {
        this.creationTokenId = creationTokenId;
    }


}
