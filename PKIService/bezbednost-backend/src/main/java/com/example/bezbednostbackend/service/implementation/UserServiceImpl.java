package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Override
    public User getById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) return user.get();
        return null;
    }



    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public void create(User employee) {
        userRepository.save(employee);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(User employee) {
        userRepository.save(employee);
    }
}
