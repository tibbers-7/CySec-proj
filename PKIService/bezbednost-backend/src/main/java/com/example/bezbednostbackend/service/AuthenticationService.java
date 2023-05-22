package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.dto.RegistrationResponseDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthenticationService {
    RegistrationResponseDTO makeRegistrationRequest(RegistrationDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException;

    boolean usernameExists(String username);

    RegistrationResponseDTO requestIsValid(String username, String workTitle);

    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    boolean workTitleIsValid(String workTitle);
    void cancelRegistrationRequest(RegistrationCancellationDTO dto);

    void approveRegistrationRequest(RegistrationCancellationDTO dto);
}
