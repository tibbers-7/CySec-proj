package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.RegistrationDTO;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController{

@Autowired
private UserService userService;

    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(RegistrationDTO dto){
        //treba resiti cist nacin na koji ce se vratiti da li je uspesno ili ne
        String response = userService.registerUser(dto);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}