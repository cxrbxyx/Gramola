package com.gramlolabe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importa HttpStatus
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException; // Importa ResponseStatusException

import com.gramlolabe.entity.Token;
import com.gramlolabe.entity.User;
import com.gramlolabe.repository.TokenDAO;
import com.gramlolabe.repository.UserDAO;

@Service
public class UserService {

    // ... (Tu código existente: DAOs, mailSender, passwordEncoder, método register) ...
    
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TokenDAO tokenDAO;

    @Autowired
    private JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(String email, String bar, String clientId, String clientSecret, String gramolaCookie, String pwd, String creationTokenId) {
        // ... (Tu método register que implementamos en el paso 2.2) ...
    }

    private void sendConfirmationEmail(String userEmail, String tokenId) {
        // ... (Tu método sendConfirmationEmail que implementamos en el paso 2.2) ...
    }


    // --- MÉTODO NUEVO PARA EL PASO 2.3 ---
    
    /**
     * Confirma un token de registro.
     * Busca el token por su ID y email, y si es válido (existe y no ha sido usado),
     * lo marca como usado.
     * * @param email El email del usuario
     * @param tokenId El ID del token
     * @throws ResponseStatusException si el token no es válido o ya fue usado
     */
    public void confirmToken(String email, String tokenId) {
        // Busca el token usando el método que ya tienes en tu TokenDAO
        Token token = tokenDAO.findByIdAndEmail(tokenId, email);

        // Comprueba si el token es válido
        if (token == null || token.getUseTime() != 0) {
            // Si no existe o ya fue usado, lanza un error
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token no válido o expirado.");
        }

        // Si es válido, marca la hora de uso y actualiza la BD
        token.setUseTime(System.currentTimeMillis());
        tokenDAO.save(token);
        
        // Opcional: Aquí también podrías activar al usuario en la tabla 'User'
        // si tienes un campo 'boolean active'.
        // User user = userDAO.findById(email).orElse(null);
        // if (user != null) {
        //    user.setActive(true);
        //    userDAO.save(user);
        // }
    }
}