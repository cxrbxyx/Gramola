package com.carbayo.gramola.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; // ¡Asegúrate de importar esto!

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carbayo.gramola.model.User;
import com.carbayo.gramola.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200") // <--- ESTA LÍNEA ES VITAL
public class UserController {

    @Autowired
    private UserService userService;

    // ... resto de métodos (getAllUsers, getUserById, etc.)

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Verifica que los campos de Spotify no vengan vacíos si son obligatorios
            if (user.getSpotifyClientId() == null || user.getSpotifyClientSecret() == null) {
                return ResponseEntity.badRequest().body("Faltan las credenciales de Spotify.");
            }

            String siteURL = "http://localhost:4200"; 
            User newUser = userService.registerUser(user, siteURL);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el error real en la consola de Java
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar: " + e.getMessage());
        }
    }

	/*
	 * @GetMapping("/verify") public ResponseEntity<String>
	 * verifyUser(@Param("code") String code) { if (userService.verify(code)) {
	 * return ResponseEntity.ok("¡Verificación exitosa! Ya puedes iniciar sesión.");
	 * } else { return
	 * ResponseEntity.badRequest().body("Fallo en la verificación."); } }
	 */
    
    // ... deleteUser, etc.
}