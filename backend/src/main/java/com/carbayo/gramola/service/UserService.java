package com.carbayo.gramola.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.carbayo.gramola.model.User;
import com.carbayo.gramola.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Lógica de Registro con Envío de Email
    public User registerUser(User user, String siteURL) {
        // 1. Generar código de verificación aleatorio
        String randomCode = UUID.randomUUID().toString();
        user.setVerificationCode(randomCode);
        user.setEnabled(false); // Desactivado por defecto
        
        // 2. Guardar usuario
        User savedUser = userRepository.save(user);

        // 3. Enviar email de confirmación
        sendVerificationEmail(savedUser, siteURL);
        
        return savedUser;
    }

    private void sendVerificationEmail(User user, String siteURL) {
        String toAddress = user.getEmail();
        String fromAddress = "no-reply@gramola.com"; // O tu email configurado
        String senderName = "Equipo Gramola";
        String subject = "Por favor, verifica tu registro en Gramola";
        
        // Construimos el enlace. siteURL suele ser http://localhost:4200 (frontend)
        // El frontend recibirá el token y llamará al backend para validarlo.
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        String content = "Hola " + user.getName() + ",\n\n"
                + "Gracias por registrar tu bar en Gramola. Por favor, haz clic en el siguiente enlace para verificar tu cuenta:\n\n"
                + verifyURL + "\n\n"
                + "Gracias,\n"
                + senderName;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }

    // Método para verificar la cuenta cuando llega el token
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null); // Limpiamos el código
            user.setEnabled(true);          // Activamos la cuenta
            userRepository.save(user);
            return true;
        }
    }
}