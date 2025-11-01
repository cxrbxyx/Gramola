package com.gramlolabe.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // Es buena práctica añadirlo
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gramlolabe.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users") // Buena práctica: agrupar endpoints
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String bar = body.get("bar");
            String clientId = body.get("clientId");
            String clientSecret = body.get("clientSecret");
            String pwd1 = body.get("pwd1");
            String pwd2 = body.get("pwd2");

            // Comprobación de contraseñas (pwd1 vs pwd2)
            if (pwd1 == null || !pwd1.equals(pwd2)) {

                return ResponseEntity.status(406).body("Las contraseñas no coinciden o están vacías");
            }

            // GramolaCookie y creationTokenId no vienen del formulario
            String gramolaCookie = "";
            // El creationTokenId se genera en el servicio, así que pasamos un valor
            // temporal
            String creationTokenId = "";

            // Llama al servicio (ahora es void y puede lanzar excepciones)
            service.register(email, bar, clientId, clientSecret, gramolaCookie, pwd1, creationTokenId);

            return ResponseEntity.ok("¡Registro casi completo! Revisa tu email para confirmar la cuenta.");

        } catch (ResponseStatusException e) {
            // Captura los errores de validación (BAD_REQUEST, CONFLICT) del servicio
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            // Captura cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado en el servidor: " + e.getMessage());
        }
    }

    @GetMapping("/confirmToken/{email}")
    public void confirmToken(@PathVariable String email,
            @RequestParam String token,
            HttpServletResponse response) throws IOException {
        try {
            // 1. Llama al servicio para validar y marcar el token [cite: 102]
            this.service.confirmToken(email, token);

            // 2. Si tiene éxito, redirige al frontend a la página de pago [cite: 102, 106]
            // Pasamos el token para que el frontend sepa de qué usuario se trata.
            response.sendRedirect("http://localhost:4200/payment?token=" + token);

        } catch (ResponseStatusException e) {
            // 3. Si el token no es válido, puedes redirigir a una página de error
            // o simplemente enviar una respuesta de error.
            // Por simplicidad, redirigimos a una página de "error" (que aún no existe).
            response.sendRedirect("http://localhost:4200/token-invalido");
        }
    }
}