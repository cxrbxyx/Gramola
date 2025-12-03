package com.carbayo.gramola.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carbayo.gramola.model.User;
import com.carbayo.gramola.service.UserService;

// Para permitir peticiones desde Angular (ajustar puerto según sea necesario)
// import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/users")
// @CrossOrigin(origins = "http://localhost:4200") 
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint de registro modificado para enviar email
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Pasamos la URL base del Frontend para armar el link del correo
            // En desarrollo local suele ser http://localhost:4200
            String siteURL = "http://localhost:4200"; 
            User newUser = userService.registerUser(user, siteURL);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar: " + e.getMessage());
        }
    }

    // Endpoint normal de creación (si se requiere sin email, mantener separado)
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    // Endpoint de verificación de cuenta
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return ResponseEntity.ok("¡Verificación exitosa! Ya puedes iniciar sesión en tu Gramola.");
        } else {
            return ResponseEntity.badRequest().body("Fallo en la verificación: Código inválido o cuenta ya activada.");
        }
    }
}