package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.RegistrationApprovalDTO;
import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.service.AuthenticationService;
import com.example.bezbednostbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController{

@Autowired
private final UserService userService;
@Autowired
private final AuthenticationService authenticationService;

    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendRegistrationRequest(@RequestBody @Validated RegistrationDTO dto) {
        try{
            authenticationService.makeRegistrationRequest(dto);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Request sent!", HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //ovo samo admin moze
    @PostMapping(value="/register/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelRegistration(RegistrationCancellationDTO dto) {
        //treba resiti cist nacin na koji ce se vratiti da li je uspesno ili ne
        authenticationService.cancelRegistrationRequest(dto);
        return new ResponseEntity<String>("request cancelled", HttpStatus.OK);
    }

    //ovo samo admin moze
    @PostMapping(value="/register/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approveRegistration(RegistrationApprovalDTO dto) {
        //treba resiti cist nacin na koji ce se vratiti da li je uspesno ili ne
        authenticationService.approveRegistrationRequest(dto);
        return new ResponseEntity<String>("request approve", HttpStatus.OK);
    }
}