// gramolabe/src/main/java/com/gramlolabe/controller/UserController.java
package com.gramlolabe.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gramlolabe.service.UserService;

@RestController
// La URL de CrossOrigin es correcta para desarrollo local
@CrossOrigin(origins = "http://localhost:4200") 
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register") // Esta URL es la correcta
    public String register(@RequestBody Map<String, String> body) {
        // Lee los campos que SÍ manda el frontend
        String email = body.get("email");
        String bar = body.get("bar");
        String clientId = body.get("clientId");
        String clientSecret = body.get("clientSecret");
        String pwd1 = body.get("pwd1"); // Lee pwd1
        String pwd2 = body.get("pwd2"); // Lee pwd2

        if (pwd1 == null || !pwd1.equals(pwd2)) {
            // Deberías devolver un error HTTP 400 o 406
            return "Las contraseñas no coinciden o están vacías";
        }
        String gramolaCookie = ""; 
        String creationTokenId = "";

    // Llama a tu servicio con los datos correctos
    String tokenId = service.register(email, bar, clientId, clientSecret, gramolaCookie, pwd1, creationTokenId);
    // Simula el envío de correo devolviendo la URL de confirmación
    String confirmUrl = "http://localhost:8080/confirm?token=" + tokenId + "&email=" + email;
    return "Usuario registrado. Confirma tu cuenta usando la siguiente URL: " + confirmUrl;
    }
}