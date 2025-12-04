package com.carbayo.gramola.service;

import java.util.List;

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
    public void register(String bar, String email, String pwd, String clientId, String clientSecret) throws Exception {
    	String token = java.util.UUID.randomUUID().toString();
        User user = new User(email,bar,pwd,clientId,clientSecret,token);
        this.userRepository.save(user);
        // 5. "Enviar" correo electrónico
        String confirmationUrl = "http://localhost:8080/users/confirmToken/" + email + "?token=" + token;
        // TRUCO PARA DESARROLLO: Como probablemente no tengas servidor SMTP configurado aún,
        // imprime esto en la consola de Eclipse/IntelliJ para poder hacer clic y seguir la práctica.
        System.out.println("--- CORREO SIMULADO ---");
        System.out.println("Hola " + bar + ", confirma tu cuenta aquí: " + confirmationUrl);
        System.out.println("-----------------------");
    }

    private void sendVerificationEmail(User user, String siteURL) {
        String toAddress = user.getEmail();
        String fromAddress = "pruebaspablo0705@gmail.com"; // O tu email configurado
        String senderName = "Equipo Gramola";
        String subject = "Por favor, verifica tu registro en Gramola";
        
        // Construimos el enlace. siteURL suele ser http://localhost:4200 (frontend)
        // El frontend recibirá el token y llamará al backend para validarlo.

        String content = "Hola " + user.getBarName() + ",\n\n"
                + "Gracias por registrar tu bar en Gramola. Por favor, haz clic en el siguiente enlace para verificar tu cuenta:\n\n"
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
	/*
	 * public boolean verify(String verificationCode) { User user =
	 * userRepository.findByVerificationCode(verificationCode);
	 * 
	 * if (user == null) { return false; } else { userRepository.save(user); return
	 * true; } }
	 */
}