package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.auth.JwtService;
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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private final JwtService jwtService;
    @Value("${jwtRecoveryExpirationMs}")
    private Long expirationTime;
    @Override
    public Optional<RecoveryToken> findByUser_Username(String username) {
        return recoveryTokenRepository.findByToken(username);
    }

    @Override
    public boolean validateToken(String token, String username) throws NoSuchAlgorithmException, InvalidKeyException {

        //if (!findByUser_Username(username).isPresent()) throw new RuntimeException("Recovery token for this user doesn't exist");
        RecoveryToken recoveryToken = findByUser_Username(token).get();

        recoveryToken.setToken(jwtService.calculateHMACOfToken(recoveryToken.getToken()));
        if(!recoveryToken.getToken().equals(token)) throw new RuntimeException("Usernames don't match");

        int comparisonResult = Instant.now().compareTo(recoveryToken.getExpiryDate());
        if (comparisonResult > 0) throw new RuntimeException("Recovery token expired");

        return true;

    }

    @Override
    public RecoveryToken createRecoveryToken(Integer userId) throws NoSuchAlgorithmException, InvalidKeyException {
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
