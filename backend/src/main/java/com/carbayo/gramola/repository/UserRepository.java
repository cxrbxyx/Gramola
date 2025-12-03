package com.carbayo.gramola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carbayo.gramola.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);

    // Nuevo método para encontrar por código de verificación
    User findByVerificationCode(String verificationCode);
}