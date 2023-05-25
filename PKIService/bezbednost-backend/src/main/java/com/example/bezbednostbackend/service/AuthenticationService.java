package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.dto.RegistrationApprovalDTO;
import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.dto.RegistrationResponseDTO;
import com.example.bezbednostbackend.exceptions.RequestAlreadyPendingException;
import com.example.bezbednostbackend.exceptions.UserAlreadyExistsException;
import com.example.bezbednostbackend.exceptions.UserIsBannedException;
import com.example.bezbednostbackend.model.AuthenticationRequest;
import com.example.bezbednostbackend.model.AuthenticationResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthenticationService {
    RegistrationResponseDTO makeRegistrationRequest(RegistrationDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException, UserIsBannedException, UserAlreadyExistsException, RequestAlreadyPendingException;

    boolean usernameExists(String username);

    void checkUsernameValidity(String username) throws UserAlreadyExistsException, RequestAlreadyPendingException, UserIsBannedException;

    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    void cancelRegistrationRequest(RegistrationCancellationDTO dto);

    void approveRegistrationRequest(RegistrationApprovalDTO dto);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
