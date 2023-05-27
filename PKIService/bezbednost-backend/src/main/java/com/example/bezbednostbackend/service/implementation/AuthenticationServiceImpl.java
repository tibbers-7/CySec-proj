package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.auth.JwtService;
import com.example.bezbednostbackend.dto.RegistrationApprovalDTO;
import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.enums.Role;
import com.example.bezbednostbackend.exceptions.RequestAlreadyPendingException;
import com.example.bezbednostbackend.exceptions.TokenRefreshException;
import com.example.bezbednostbackend.exceptions.UserAlreadyExistsException;
import com.example.bezbednostbackend.exceptions.UserIsBannedException;
import com.example.bezbednostbackend.dto.AuthenticationRequestDTO;
import com.example.bezbednostbackend.dto.AuthenticationResponseDTO;
import com.example.bezbednostbackend.model.RegistrationRequest;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.model.token.VerificationToken;
import com.example.bezbednostbackend.model.token.RefreshToken;
import com.example.bezbednostbackend.repository.AddressRepository;
import com.example.bezbednostbackend.repository.RegistrationRequestRepository;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.repository.VerificationTokenRepository;
import com.example.bezbednostbackend.service.AuthenticationService;
import com.example.bezbednostbackend.service.EmailService;
import com.example.bezbednostbackend.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
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
    private final AuthenticationManager myAuthenticationManager;
    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RefreshTokenService refreshTokenService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;



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
        sendRequestApprovalEmail(request.getUsername());
    }

    public void createUserFromRegistrationRequest(RegistrationRequest request){
        User registratedUser = new User(1, request.getName(),request.getSurname(),
                request.getUsername(),request.getPassword(),request.getAddress(),
                request.getPhoneNumber(), Role.valueOf(request.getRole()),false);
        userRepository.save(registratedUser);
        addressRepository.save(request.getAddress());
    }

    @SneakyThrows
    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        myAuthenticationManager.authenticate(authRequest);
        var user=userRepository.findByUsername(request.getUsername());
        if(user==null) throw(new UsernameNotFoundException("User not found"));
        if (!user.isActive()) throw new UserIsBannedException("User not activated");
        var accessToken=jwtService.generateAccessToken(user);
        var refreshToken=refreshTokenService.createRefreshToken(user.getId());
        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }


    public void sendRequestApprovalEmail(String username ){
        log.info("AuthenticationService: entered the sendRequestApprovalEmail method.");
        String token = UUID.randomUUID().toString();
        createVerificationToken(username, token);
        String emailContent = "Hello " + username + "," + "\r\n" +
                "Your account was succesfully approved.\n" +
                "Please activate your account with this link:\n" +
                "http://localhost:8082/auth/activateAccount?token="+token+"&username="+username;
        String emailSubject = "Registration request acceptance";


        emailService.sendSimpleEmail( username, emailSubject, emailContent );
    }

    private VerificationToken createVerificationToken(String username,String token){
        VerificationToken verificationToken = new VerificationToken(username, token);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public boolean activateAccount(String username, String token){
        User user=userRepository.findByUsername(username);
        Optional<VerificationToken> tokenFromDb=verificationTokenRepository.findByUsername(username);
        if(tokenFromDb==null) return false;
        if (tokenFromDb.get().getToken().equals(token)){
            user.setActive(true);
            userRepository.save(user);
            return true;
        } return false;

    }
    public String refreshToken(AuthenticationResponseDTO dto) throws TokenRefreshException {
            if(!jwtService.JwtSignatureIsValid(dto.getAccessToken()))
                throw new TokenRefreshException(dto.getAccessToken(),"Access token is invalid!");
        //proveri se refresh token iz baze da li je isti kao u dto
            Optional<RefreshToken> token = refreshTokenService.findByToken(dto.getRefreshToken());
            if(token.isEmpty()) throw new TokenRefreshException(dto.getRefreshToken(), "No such refresh token exists, access denied");
            RefreshToken tokenFromDB = token.get();
            if (refreshTokenService.isExpired(tokenFromDB)) throw new TokenRefreshException(dto.getRefreshToken(), "Refresh token is expired, access denied");
            Optional<User> user = userRepository.findById(tokenFromDB.getUser().getId());
            if(user.isEmpty() || !user.get().isActive()) throw new TokenRefreshException(dto.getRefreshToken(), "User not valid, access denied");
            else return jwtService.generateAccessToken(tokenFromDB.getUser());
    }




}
