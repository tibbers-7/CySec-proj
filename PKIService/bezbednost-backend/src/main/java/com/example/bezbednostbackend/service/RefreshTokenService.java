package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.token.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken createRefreshToken(Integer userId);
    boolean isExpired(RefreshToken token);
    int deleteByUserId(Integer userId);
}
