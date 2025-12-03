package com.carbayo.gramola.controller;

import com.carbayo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin // Permitir peticiones desde Angular (localhost:4200)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Map<String, String> body) {
        String bar = body.get("bar");
        String email = body.get("email");
        String pwd1 = body.get("pwd1");
        String pwd2 = body.get("pwd2");
        String clientId = body.get("clientId");
        String clientSecret = body.get("clientSecret");

        // 1. Validaciones básicas
        if (!pwd1.equals(pwd2)) {
             // Devolvemos 406 si los datos no sirven (ej. contraseñas no coinciden) [cite: 112]
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            // 2. Llamada al servicio
            this.userService.register(bar, email, pwd1, clientId, clientSecret);
            return new ResponseEntity<>(HttpStatus.OK); // 200 OK [cite: 112]
        } catch (Exception e) {
            // 409 si el usuario ya existe [cite: 112]
            return new ResponseEntity<>(HttpStatus.CONFLICT); 
        }
    }
}
