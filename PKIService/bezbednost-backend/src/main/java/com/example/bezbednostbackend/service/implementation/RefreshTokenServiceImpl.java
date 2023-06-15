package com.example.bezbednostbackend.service.implementation;


import com.example.bezbednostbackend.exceptions.TokenRefreshException;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.model.token.RefreshToken;
import com.example.bezbednostbackend.repository.RefreshTokenRepository;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private final UserRepository userRepository;

    @Value("${jwtRefreshExpirationMs}")
    private Long expirationTime;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Integer userId){
        Optional<User> user=userRepository.findById(userId);
        if(!user.isPresent()) throw new NoSuchElementException("User not found");

        User _user=user.get();
        if(!_user.isAllowRefreshToken()) throw new RuntimeException("Refresh token is blocked for this user!");

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(_user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(expirationTime));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public boolean isExpired(RefreshToken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return true;
           }
        return false;
    }

    @Transactional
    public int deleteByUserId(Integer userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}