package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User getById(Integer id);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    void create(User employee);

    void delete(Integer id);

    void update(User employee);

    UserDetails loadUserByUsername(String username);
}
