package com.gramlolabe.repository;

import com.gramlolabe.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDAO extends JpaRepository<Token, String> {
    Token findByIdAndEmail(String id, String email);
}
