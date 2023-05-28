package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.auth.HmacUtils;
import com.example.bezbednostbackend.auth.JwtService;
import com.example.bezbednostbackend.dto.RegistrationApprovalDTO;
import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.exceptions.RequestAlreadyPendingException;
import com.example.bezbednostbackend.exceptions.TokenRefreshException;
import com.example.bezbednostbackend.exceptions.UserAlreadyExistsException;
import com.example.bezbednostbackend.exceptions.UserIsBannedException;
import com.example.bezbednostbackend.dto.AuthenticationRequestDTO;
import com.example.bezbednostbackend.dto.AuthenticationResponseDTO;
import com.example.bezbednostbackend.model.RegistrationRequest;
import com.example.bezbednostbackend.model.Role;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.model.token.VerificationToken;
import com.example.bezbednostbackend.model.token.RefreshToken;
import com.example.bezbednostbackend.repository.*;
import com.example.bezbednostbackend.service.AuthenticationService;
import com.example.bezbednostbackend.service.EmailService;
import com.example.bezbednostbackend.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import java.util.*;


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
    @Autowired
    RoleRepository roleRepository;

    private final String hmacSHA256Value = "5b50d80c7dc7ae8bb1b1433cc0b99ecd2ac8397a555c6f75cb8a619ae35a0c35";
    private final String hmacSHA256Algorithm = "HmacSHA256";
    private final String key = "879378747292";

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
                dto.getUsername(), dto.getPassword(), dto.getAddress(), dto.getPhoneNumber(), dto.getRole(), dto.getWorkTitle(), false, false);
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
    public void approveRegistrationRequest(RegistrationApprovalDTO dto) throws NoSuchAlgorithmException, InvalidKeyException {
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
        User registratedUser = new User(request.getName(),request.getSurname(),
                request.getUsername(),request.getPassword(),request.getAddress(),
                request.getPhoneNumber(), Arrays.asList(roleRepository.findByName(request.getRole())),request.getWorkTitle(),false);

        userRepository.save(registratedUser);
        addressRepository.save(request.getAddress());
    }

    @SneakyThrows
    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        log.info("AuthenticationService: Entered Authenticate method.");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        log.info("AuthenticationService:"+authRequest);
        myAuthenticationManager.authenticate(authRequest);
        log.info("AuthenticationService:"+request.getUsername());
        var user=userRepository.findByUsername(request.getUsername());
        log.info("AuthenticationService: Got user.");

        if(user==null) throw(new UsernameNotFoundException("User not found"));
        if (!user.isActive()) throw new UserIsBannedException("User not activated");
        var accessToken=jwtService.generateAccessToken(addClaims(user), user);
        var refreshToken=refreshTokenService.createRefreshToken(user.getId());
        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public Map<String,Object> addClaims(User user){
       Map<String,Object> claims = new HashMap<>();
       claims.put("username", user.getUsername());
        for (Role role : user.getRoles()) {
            claims.put("role"+role.getId(),role.getName());
        }
       claims.put("userId", String.valueOf(user.getId()));
       return claims;
    }


    public void sendRequestApprovalEmail(String username ) throws NoSuchAlgorithmException, InvalidKeyException {
        log.info("AuthenticationService: entered the sendRequestApprovalEmail method.");
        String token = UUID.randomUUID().toString();
        String hmacToken = HmacUtils.hmacWithJava(hmacSHA256Algorithm, token, key);
        createVerificationToken(username, hmacToken, 1440);
        String emailContent = "Hello " + username + "," + "\r\n" +
                "Your account was succesfully approved.\n" +
                "Please activate your account with this link:\n" +
                "http://localhost:8082/auth/activateAccount?token="+hmacToken+"&username="+username;
        String emailSubject = "Registration request acceptance";


        emailService.sendSimpleEmail( username, emailSubject, emailContent );
    }

    private VerificationToken createVerificationToken(String username,String token, int expiryTimeInMinutes){
        VerificationToken verificationToken = new VerificationToken(username, token, expiryTimeInMinutes);
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

    @Override
    public void passwordlessLogin(String username) throws UserIsBannedException, NoSuchAlgorithmException, InvalidKeyException {
        log.info("AuthenticationService: entered the passwordlessLogin method.");
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User with this username doesn't exist");
        else if (!user.isActive()) throw new UserIsBannedException("Account with this username is inactive");
        String token = UUID.randomUUID().toString();
        createVerificationToken(username, token, 10);
        String hmac = jwtService.calculateHMACOfToken(token);
        String emailContent = "Hello " + user.getName() + "," + "\r\n" +
                "Use this link to log into your account." + "\r\n" +
                "http://localhost:8082/auth/authenticate/link?token="+token+"&username="+username+"&hmac="+hmac;
        String emailSubject = "Sign in to your account";
        emailService.sendSimpleEmail( username, emailSubject, emailContent );
    }

    @Override
    public AuthenticationResponseDTO logInWithLink(String token, String username, String hmac) throws Exception {
        Optional<VerificationToken> optional = verificationTokenRepository.findByUsername(username);
        if(optional.isEmpty()) throw new Exception("No token for this user exists!");
        VerificationToken tokenFromDB = optional.get();
        //ne treba nam vise ovaj token pa ga brisemo, proveriti da li ce ovo raditi sve
        verificationTokenRepository.delete(tokenFromDB);
        if(tokenFromDB.getExpiryDate().isBefore(LocalDateTime.now())) throw new Exception("This token is expired!");
        if(!jwtService.calculateHMACOfToken(tokenFromDB.getToken()).equals(hmac) || !tokenFromDB.getToken().equals(token)) throw new Exception("This token has been tampered with!");
        var user=userRepository.findByUsername(username);
        if(user==null) throw(new UsernameNotFoundException("User not found"));
        if (!user.isActive()) throw new UserIsBannedException("User not activated");
        var accessToken=jwtService.generateAccessToken(user);
        var refreshToken=refreshTokenService.createRefreshToken(user.getId());
        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public void deleteSession(String refreshToken) throws TokenRefreshException {
        Optional<RefreshToken> optional = refreshTokenService.findByToken(refreshToken);
        if(optional.isEmpty()) throw new TokenRefreshException(refreshToken, "No such token in database");
        refreshTokenService.deleteByUserId(optional.get().getUser().getId());
    }


}
