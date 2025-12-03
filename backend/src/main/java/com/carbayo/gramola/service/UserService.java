package com.carbayo.gramola.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbayo.gramola.model.User;
import com.carbayo.gramola.repository.UserRepository;

@Service // <--- VITAL: Sin esto, el Controller no encuentra este servicio
public class UserService {

    @Autowired // <--- VITAL: Esto conecta la interfaz 'vacía' con tu código
    private UserRepository userRepository;

    public void register(String bar, String email, String pwd, String clientId, String clientSecret) throws Exception {
        
        // 1. Verificar si existe el usuario (Usamos el método que nos regala CrudRepository)
        if (this.userRepository.existsById(email)) {
            throw new Exception("El usuario ya existe");
        }
        
        // 2. Crear usuario nuevo
        User user = new User();
        user.setEmail(email);
        user.setBar(bar);
        user.setPwd(pwd); // En un caso real, aquí usaríamos BCrypt para hashear
        user.setClientId(clientId);
        user.setClientSecret(clientSecret);
        
        // 3. Generar Token de confirmación (usamos UUID para que sea único)
        String token = UUID.randomUUID().toString();
        user.setCreationTokenId(token);
        
        // 4. Guardar en base de datos
        // El método .save() viene "gratis" en el repositorio, no tienes que programarlo.
        this.userRepository.save(user);

        // 5. "Enviar" correo electrónico
        // OJO: El puerto 4200 es donde corre Angular, pero el link debe apuntar a tu Backend (8080)
        // para confirmar, o al Frontend si prefieres manejarlo allí. 
        // Según el PDF, el usuario hace clic y va al backend para validar.
        String confirmationUrl = "http://localhost:8080/users/confirmToken?email=" + email + "&token=" + token;
        
        System.out.println("----------------------------------------------------");
        System.out.println("SIMULACIÓN DE EMAIL PARA: " + bar);
        System.out.println("Haz clic aquí para confirmar: " + confirmationUrl);
        System.out.println("----------------------------------------------------");
    }
    
    // Método extra que necesitarás para el paso siguiente (confirmar cuenta)
    public boolean confirmUser(String email, String token) {
        // Usamos el método personalizado que definimos en la interfaz
        return userRepository.findByCreationTokenId(token)
                .map(user -> {
                    if (user.getEmail().equals(email)) {
                        // Aquí podrías borrar el token o marcar al usuario como activo
                        user.setCreationTokenId(null); // Borramos token para que no se use 2 veces
                        userRepository.save(user);
                        return true;
                    }
                    return false;
                })
                .orElse(false);
    }
}