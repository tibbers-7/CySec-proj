package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User getById(Integer id);
    UserDetails loadUserByUsername(String username);

}
