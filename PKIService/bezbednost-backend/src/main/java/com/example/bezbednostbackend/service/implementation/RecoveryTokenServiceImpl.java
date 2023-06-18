package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.model.token.RecoveryToken;
import com.example.bezbednostbackend.model.token.RefreshToken;
import com.example.bezbednostbackend.repository.RecoveryTokenRepository;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.service.RecoveryTokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecoveryTokenServiceImpl implements RecoveryTokenService {

    private final RecoveryTokenRepository recoveryTokenRepository;
    private final UserRepository userRepository;
    @Value("${jwtRecoveryExpirationMs}")
    private Long expirationTime;
    @Override
    public Optional<RecoveryToken> findByToken(String token) {
        return recoveryTokenRepository.findByToken(token);
    }

    @Override
    public boolean validateToken(String token, String username) {
        if (!findByToken(token).isPresent()) throw new RuntimeException("Recovery token doesn't exist");
        RecoveryToken recoveryToken = findByToken(token).get();

        if(!recoveryToken.getUser().getUsername().equals(username)) throw new RuntimeException("Usernames don't match");

        int comparisonResult = Instant.now().compareTo(recoveryToken.getExpiryDate());
        if (comparisonResult > 0) throw new RuntimeException("Recovery token expired");

        return true;

    }

    @Override
    public RecoveryToken createRecoveryToken(Integer userId){
        Optional<User> user=userRepository.findById(userId);
        if(!user.isPresent()) throw new NoSuchElementException("User not found");


        RecoveryToken recoveryToken = new RecoveryToken();

        recoveryToken.setUser(user.get());
        recoveryToken.setExpiryDate(Instant.now().plusMillis(expirationTime));
        recoveryToken.setToken(UUID.randomUUID().toString());

        recoveryToken = recoveryTokenRepository.save(recoveryToken);
        return recoveryToken;
    }

}
