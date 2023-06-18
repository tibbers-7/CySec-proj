package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.model.token.RecoveryToken;

import java.util.Optional;

public interface RecoveryTokenService {
    Optional<RecoveryToken> findByToken(String token);
    boolean validateToken(String token,String username);
    RecoveryToken createRecoveryToken(Integer userId);

}
