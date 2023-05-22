package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.dto.RegistrationResponseDTO;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.service.AuthenticationService;
import com.example.bezbednostbackend.service.UserService;
import com.example.bezbednostbackend.service.implementation.AuthenticationServiceImpl;
import com.example.bezbednostbackend.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/user")
public class UserController{

@Autowired
private UserService userService;
@Autowired
private AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendRegistrationRequest(@RequestBody RegistrationDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RegistrationResponseDTO response = authenticationService.makeRegistrationRequest(dto);
        if(response.isValid()) return new ResponseEntity<>(response.getResponse(), HttpStatus.OK);
        return new ResponseEntity<>(response.getResponse(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //ovo samo admin moze
    @PostMapping(value="/register/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelRegistration(RegistrationCancellationDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //treba resiti cist nacin na koji ce se vratiti da li je uspesno ili ne
        authenticationService.cancelRegistrationRequest(dto);
        return new ResponseEntity<String>("request cancelled", HttpStatus.OK);
    }
}