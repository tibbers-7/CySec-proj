package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.config.JwtService;
import com.example.bezbednostbackend.dto.RegistrationApprovalDTO;
import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.dto.RegistrationResponseDTO;
import com.example.bezbednostbackend.exceptions.RequestAlreadyPendingException;
import com.example.bezbednostbackend.exceptions.UserAlreadyExistsException;
import com.example.bezbednostbackend.exceptions.UserIsBannedException;
import com.example.bezbednostbackend.dto.AuthenticationRequestDto;
import com.example.bezbednostbackend.model.AuthenticationResponse;
import com.example.bezbednostbackend.model.RegistrationRequest;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.repository.RegistrationRequestRepository;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.service.AuthenticationService;
import com.example.bezbednostbackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RegistrationRequestRepository registrationRequestRepository;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final JwtService jwtService;


    private final AuthenticationManager authenticationManager;


    @Override
    public RegistrationResponseDTO makeRegistrationRequest(RegistrationDTO dto) throws NoSuchAlgorithmException,
            InvalidKeySpecException, UserIsBannedException,
            UserAlreadyExistsException, RequestAlreadyPendingException {

        checkUsernameValidity(dto.getUsername());
        String hashedPassword = hashPassword(dto.getPassword());

        RegistrationRequest request = new RegistrationRequest(dto.getName(), dto.getSurname(),
                dto.getUsername(), hashedPassword, dto.getAddress(), dto.getPhoneNumber(),
                dto.getWorkTitle(), LocalDateTime.now(),  LocalDateTime.now(), false, false);

        registrationRequestRepository.save(request);
        return new RegistrationResponseDTO("Request sent!", true);
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
    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //koristimo PBKDF2 hashing algoritam
        //kreiramo salt za hashovanje
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        //instanciramo, 65536 je strength, tj koliko iteracija traje algoritam
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new String(hash, StandardCharsets.UTF_8);
    }

    @Override
    public void cancelRegistrationRequest(RegistrationCancellationDTO dto){
        //mora se poslati mejl korisniku sa detaljima odbijanja,
        // prmeniti ulazni parametar na neki dto koji sadrzi opis admina zasto je odbio, vreme blokiranja
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
        String emailContent = "Hello " + name + "," + "\r\n" +
                "Unfortunately, the registration request that you sent has been denied. "+
                "This means that you will not be able to send another request for the next three days. "+
                "The reason for the request denial was: " + cancellationDescription;
        String emailSubject = "Registration request cancellation";
        emailService.sendSimpleEmail( username, emailSubject, emailContent );
    }

    @Override
    public void approveRegistrationRequest(RegistrationApprovalDTO dto){
        Optional<RegistrationRequest> optionalRequest =
                registrationRequestRepository.findById(dto.getIdOfRequest());
        //kad se odobri mora se dodati u usere
        if(optionalRequest.isEmpty()) return;
        RegistrationRequest request = optionalRequest.get();
        request.setCancelled(false);
        request.setResolved(true);
        registrationRequestRepository.save(request);
        User registratedUser = new User(1, request.getName(),request.getSurname(),
                request.getUsername(),request.getPassword(),request.getAddress(),
                request.getPhoneNumber(),request.getWorkTitle(), false,null);
        //TODO: promeniti iz null u role
        userRepository.save(registratedUser);
        sendRequestApprovalEmail(request.getName(), dto.getApprovalDescription(), request.getUsername());
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                        )
        );
        var user=userRepository.findByUsername(request.getUsername());
        if(user==null) throw(new UsernameNotFoundException("User not found"));
        var accessToken=jwtService.generateToken(user);
        var refreshToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public void sendRequestApprovalEmail(String name, String approvalDescription, String username ){
        String token = UUID.randomUUID().toString();
        //createVerificationToken(user, token);
        String emailContent = "Hello " + name + "," + "\r\n";
        String emailSubject = "Registration request acceptance";
        emailService.sendSimpleEmail( username, emailSubject, emailContent );
    }


}
