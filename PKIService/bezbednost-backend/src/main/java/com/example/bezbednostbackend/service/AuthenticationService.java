package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.dto.RegistrationApprovalDTO;
import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.exceptions.RequestAlreadyPendingException;
import com.example.bezbednostbackend.exceptions.UserAlreadyExistsException;
import com.example.bezbednostbackend.exceptions.UserIsBannedException;
import com.example.bezbednostbackend.dto.AuthenticationRequestDTO;
import com.example.bezbednostbackend.dto.AuthenticationResponseDTO;

public interface AuthenticationService {
    void makeRegistrationRequest(RegistrationDTO dto) throws  UserIsBannedException, UserAlreadyExistsException, RequestAlreadyPendingException;

    boolean usernameExists(String username);

    void checkUsernameValidity(String username) throws UserAlreadyExistsException, RequestAlreadyPendingException, UserIsBannedException;


    void cancelRegistrationRequest(RegistrationCancellationDTO dto);

    void approveRegistrationRequest(RegistrationApprovalDTO dto);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
}
