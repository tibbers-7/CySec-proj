package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.auth.JwtService;
import com.example.bezbednostbackend.dto.RegistrationApprovalDTO;
import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.enums.Role;
import com.example.bezbednostbackend.exceptions.RequestAlreadyPendingException;
import com.example.bezbednostbackend.exceptions.UserAlreadyExistsException;
import com.example.bezbednostbackend.exceptions.UserIsBannedException;
import com.example.bezbednostbackend.dto.AuthenticationRequestDTO;
import com.example.bezbednostbackend.dto.AuthenticationResponseDTO;
import com.example.bezbednostbackend.model.RegistrationRequest;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.repository.AddressRepository;
import com.example.bezbednostbackend.repository.RegistrationRequestRepository;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.service.AuthenticationService;
import com.example.bezbednostbackend.service.EmailService;
import com.example.bezbednostbackend.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service @RequiredArgsConstructor @Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RegistrationRequestRepository registrationRequestRepository;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RefreshTokenService refreshTokenService;



    @Override
    public void makeRegistrationRequest(RegistrationDTO dto) throws UserIsBannedException,
            UserAlreadyExistsException, RequestAlreadyPendingException {
        log.info("AuthenticationService: Entered MakeRegistrationRequest method.");
        checkUsernameValidity(dto.getUsername());
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        createRegistrationRequestForDB(dto);
    }

    public void createRegistrationRequestForDB(RegistrationDTO dto){
        log.info("AuthenticationService: entered the createRegistrationRequestForDB");
        RegistrationRequest request = new RegistrationRequest(dto.getName(), dto.getSurname(),
                dto.getUsername(), dto.getPassword(), dto.getAddress(), dto.getPhoneNumber(), dto.getRole(), LocalDateTime.now(),  LocalDateTime.now(), false, false);
        registrationRequestRepository.save(request);
        log.info("AuthenticationService: makeRegistrationRequest - registration request saved to database");
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public void checkUsernameValidity(String username) throws UserAlreadyExistsException,
            RequestAlreadyPendingException, UserIsBannedException {

        if(usernameExists(username))
            throw new UserAlreadyExistsException("Username: " + username + " is already being used!");

        List<RegistrationRequest> requests = registrationRequestRepository.findByUsername(username);
        if(requests.isEmpty()) return;
        for (RegistrationRequest request: requests) {
            if(!request.isResolved())
               throw new RequestAlreadyPendingException("There is already a pending request for username " + username);
            else if(request.isCancelled() && request.getRequestUpdated().plusDays(3).isAfter(LocalDateTime.now()))
                throw new UserIsBannedException("User with username "+ username + " has been banned.");
            }
    }

    @Override
    public void cancelRegistrationRequest(RegistrationCancellationDTO dto){
        //mora se poslati mejl korisniku sa detaljima odbijanja,
        // prmeniti ulazni parametar na neki dto koji sadrzi opis admina zasto je odbio, vreme blokiranja
        log.info("AuthenticationService: entered the cancelRegistrationRequest method.");
        Optional<RegistrationRequest> optionalRequest = registrationRequestRepository.findById(dto.getIdOfRequest());
        if(optionalRequest.isEmpty()) return;
        RegistrationRequest request = optionalRequest.get();
        request.setCancelled(true);
        request.setResolved(true);
        request.setRequestUpdated(LocalDateTime.now());
        sendRequestCancellationEmail(request.getName(), dto.getCancellationDescription(), request.getUsername());
        registrationRequestRepository.save(request);
    }

    public void sendRequestCancellationEmail(String name, String cancellationDescription, String username ){
        log.info("AuthenticationService: entered the sendRequestCancellationEmail method.");
        String emailContent = "Hello " + name + "," + "\r\n" +
                "Unfortunately, the registration request that you sent has been denied. "+
                "This means that you will not be able to send another request for the next three days. "+
                "The reason for the request denial was: " + cancellationDescription;
        String emailSubject = "Registration request cancellation";
        emailService.sendSimpleEmail( username, emailSubject, emailContent );
    }

    @Override
    public void approveRegistrationRequest(RegistrationApprovalDTO dto){
        log.info("AuthenticationService: entered the approveRegistrationRequest method.");
        Optional<RegistrationRequest> optionalRequest =
                registrationRequestRepository.findById(dto.getIdOfRequest());
        if(optionalRequest.isEmpty()) return;
        RegistrationRequest request = optionalRequest.get();
        request.setCancelled(false);
        request.setResolved(true);
        registrationRequestRepository.save(request);
        createUserFromRegistrationRequest(request);
       // sendRequestApprovalEmail(request.getName(), dto.getApprovalDescription(), request.getUsername());
    }

    public void createUserFromRegistrationRequest(RegistrationRequest request){
        User registratedUser = new User(1, request.getName(),request.getSurname(),
                request.getUsername(),request.getPassword(),request.getAddress(),
                request.getPhoneNumber(), Role.valueOf(request.getRole()),false);
        userRepository.save(registratedUser);
        addressRepository.save(request.getAddress());
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        //ovde pukne, kao da se ne sacuva u njega
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                        )
        );
        var user=userRepository.findByUsername(request.getUsername());
        if(user==null) throw(new UsernameNotFoundException("User not found"));
        var accessToken=jwtService.generateAccessToken(user);
        var refreshToken=refreshTokenService.createRefreshToken(user.getId());
        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }


    public void sendRequestApprovalEmail(String name, String approvalDescription, String username ){
        log.info("AuthenticationService: entered the sendRequestApprovalEmail method.");
        String token = UUID.randomUUID().toString();
        //createVerificationToken(user, token);
        String emailContent = "Hello " + name + "," + "\r\n";
        String emailSubject = "Registration request acceptance";
        emailService.sendSimpleEmail( username, emailSubject, emailContent );
    }


}
