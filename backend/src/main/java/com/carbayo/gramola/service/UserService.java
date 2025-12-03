package com.carbayo.gramola.service;

import com.carbayo.gramola.model.User;
import com.carbayo.gramola.repository.UserRepository;
public class UserService {
	public void register(String bar, String email, String pwd, String clientId, String clientSecret) throws Exception {
	    // 1. Verificar si existe el usuario...
	    
	    // 2. Crear usuario nuevo
	    User user = new User();
	    user.setEmail(email);
	    user.setBar(bar);
	    user.setPwd(pwd); // Recuerda cifrar esto en un entorno real
	    user.setClientId(clientId);
	    user.setClientSecret(clientSecret);
	    
	    // 3. Generar Token de confirmación
	    String token = java.util.UUID.randomUUID().toString();
	    user.setCreationTokenId(token);
	    
	    // 4. Guardar en base de datos
	    this.serRepository.save(user);

	    // 5. "Enviar" correo electrónico
	    String confirmationUrl = "http://localhost:8080/users/confirmToken/" + email + "?token=" + token;
	    
	    // TRUCO PARA DESARROLLO: Como probablemente no tengas servidor SMTP configurado aún,
	    // imprime esto en la consola de Eclipse/IntelliJ para poder hacer clic y seguir la práctica.
	    System.out.println("--- CORREO SIMULADO ---");
	    System.out.println("Hola " + bar + ", confirma tu cuenta aquí: " + confirmationUrl);
	    System.out.println("-----------------------");
	}
}
