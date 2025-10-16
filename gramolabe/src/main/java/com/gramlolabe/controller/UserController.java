package com.gramlolabe.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gramlolabe.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String bar = body.get("bar");
        String clientId = body.get("clientId");
        String clientSecret = body.get("clientSecret");
        String gramolaCookie = body.get("gramolaCookie");
        String pwd = body.get("pwd");
        String creationTokenId = body.get("creationTokenId");

        if (gramolaCookie == null) gramolaCookie = "";
        if (creationTokenId == null) creationTokenId = "";

        return service.register(email, bar, clientId, clientSecret, gramolaCookie, pwd, creationTokenId);
    }
}
