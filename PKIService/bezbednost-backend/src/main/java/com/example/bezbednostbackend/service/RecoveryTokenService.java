package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.model.token.RecoveryToken;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface RecoveryTokenService {
    Optional<RecoveryToken> findByUser_Username(String username);
    boolean validateToken(String token,String username) throws NoSuchAlgorithmException, InvalidKeyException;
    RecoveryToken createRecoveryToken(Integer userId) throws NoSuchAlgorithmException, InvalidKeyException;

}
