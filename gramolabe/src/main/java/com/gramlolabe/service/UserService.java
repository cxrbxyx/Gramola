package com.gramlolabe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gramlolabe.entity.User;
import com.gramlolabe.repository.UserDAO;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private com.gramlolabe.repository.TokenDAO tokenDAO;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(String email, String bar, String clientId, String clientSecret, String gramolaCookie, String pwd, String creationTokenId) {
        if (bar == null || bar.trim().isEmpty())
            return "El nombre del bar es obligatorio";
        if (email == null || email.trim().isEmpty())
            return "El email es obligatorio";
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            return "El email no es válido";
        if (clientId == null || clientId.trim().isEmpty())
            return "El Client ID es obligatorio";
        if (clientSecret == null || clientSecret.trim().isEmpty())
            return "El Client Secret es obligatorio";
        if (pwd == null || pwd.trim().isEmpty())
            return "La contraseña es obligatoria";
        if (pwd.length() < 6)
            return "La contraseña debe tener al menos 6 caracteres";

    String hashedPwd = passwordEncoder.encode(pwd);
    // Generar token de confirmación
    String tokenId = java.util.UUID.randomUUID().toString();
    long creationTime = System.currentTimeMillis();
    com.gramlolabe.entity.Token token = new com.gramlolabe.entity.Token(tokenId, creationTime, email);
    tokenDAO.save(token);
    // Asociar el token al usuario
    User user = new User(email, bar, clientId, clientSecret, gramolaCookie, hashedPwd, tokenId);
    userDAO.save(user);

    return tokenId;
    }
}
