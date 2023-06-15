package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.token.RecoveryToken;
import com.example.bezbednostbackend.model.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken, Integer> {
    Optional<RecoveryToken> findByToken(String token);

}
