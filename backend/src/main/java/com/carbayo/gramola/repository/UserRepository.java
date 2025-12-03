package com.carbayo.gramola.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.carbayo.gramola.model.User;

import java.util.Optional;

@Repository // <--- Añade esto para ayudar a Spring a detectarlo
public interface UserRepository extends CrudRepository<User, String> {
    
    // Spring implementa esto automáticamente solo con ver el nombre del método.
    // Lo necesitaremos para el paso de confirmar el email.
    Optional<User> findByCreationTokenId(String creationTokenId);
}
