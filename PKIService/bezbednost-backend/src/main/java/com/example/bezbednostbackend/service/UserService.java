package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getById(Integer id);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    void create(User employee);

    void delete(Integer id);

    void update(User employee);
}
