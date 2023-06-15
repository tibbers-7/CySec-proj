package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.dto.RegistrationResolveRequestDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.exceptions.RequestAlreadyPendingException;
import com.example.bezbednostbackend.exceptions.TokenRefreshException;
import com.example.bezbednostbackend.exceptions.UserAlreadyExistsException;
import com.example.bezbednostbackend.exceptions.UserIsBannedException;
import com.example.bezbednostbackend.dto.AuthenticationRequestDTO;
import com.example.bezbednostbackend.dto.AuthenticationResponseDTO;
import com.example.bezbednostbackend.model.RegistrationRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface AuthenticationService {
    void makeRegistrationRequest(RegistrationDTO dto) throws  UserIsBannedException, UserAlreadyExistsException, RequestAlreadyPendingException;

    boolean usernameExists(String username);

    void checkUsernameValidity(String username) throws UserAlreadyExistsException, RequestAlreadyPendingException, UserIsBannedException;


    void cancelRegistrationRequest(RegistrationResolveRequestDTO dto);

    void approveRegistrationRequest(RegistrationResolveRequestDTO dto) throws NoSuchAlgorithmException, InvalidKeyException;

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
    void sendRequestApprovalEmail(String username) throws NoSuchAlgorithmException, InvalidKeyException;
    boolean activateAccount(String username, String tokenWithHash) throws Exception;

    String refreshToken(AuthenticationResponseDTO dto) throws TokenRefreshException;

    void passwordlessLogin(String username) throws UserIsBannedException, NoSuchAlgorithmException, InvalidKeyException;

    AuthenticationResponseDTO logInWithLink(String tokenWithHash, String username) throws Exception;

    void deleteSession(String refreshToken) throws TokenRefreshException;

    List<RegistrationRequest> getAllRegistrationRequests();
    void sendRecoveryEmail(AuthenticationRequestDTO dto) throws UserIsBannedException, NoSuchAlgorithmException, InvalidKeyException;
}
