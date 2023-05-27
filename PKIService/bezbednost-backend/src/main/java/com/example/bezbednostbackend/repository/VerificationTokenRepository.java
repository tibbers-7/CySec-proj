package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.model.token.RefreshToken;
import com.example.bezbednostbackend.model.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByUsername(String username);
}