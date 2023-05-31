package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.AuthenticationRequestDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.dto.AuthenticationResponseDTO;
import com.example.bezbednostbackend.dto.StringResponseDTO;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> sendRegistrationRequest(@RequestBody @Valid RegistrationDTO dto) {
        try{
            authenticationService.makeRegistrationRequest(dto);
        }
        catch(Exception e){
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new StringResponseDTO("Registration request sent!"), HttpStatus.OK);
    }

    @PostMapping(value="/authenticate",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping(value="/activateAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> activateAccount(
            @RequestParam String token,@RequestParam String username, @RequestParam String hmac
    ) throws Exception {
        if(authenticationService.activateAccount(username,token, hmac)){
            return new ResponseEntity<>(new StringResponseDTO("Account activated!"), HttpStatus.OK);
        } else return  new ResponseEntity<>(new StringResponseDTO("Invalid token!"), HttpStatus.BAD_REQUEST);

    }


    @PostMapping(value="/authenticate/passwordless",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> passwordlessLogin(@RequestBody String username) {
       try{
           authenticationService.passwordlessLogin(username);
       }catch(Exception e){
           return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(new StringResponseDTO("Check your email for the sign in link :)"), HttpStatus.OK);
    }

    @PostMapping(value="/authenticate/link",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDTO> logInWithLink(@RequestParam String token, @RequestParam String username, @RequestParam String hmac) {
        try{
            AuthenticationResponseDTO response = authenticationService.logInWithLink(token, username, hmac);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/refresh", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> refreshToken(@RequestBody AuthenticationResponseDTO dto){
        try{
            authenticationService.refreshToken(dto);
        }catch(Exception e){
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new StringResponseDTO("Successfully refreshed token!"), HttpStatus.OK);
    }

    @PostMapping(value="/logOut")
    public ResponseEntity<StringResponseDTO> logOut(@RequestBody String refreshToken) {
        try{
            authenticationService.deleteSession(refreshToken);
        }catch(Exception e){
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new StringResponseDTO("Session ended."), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
