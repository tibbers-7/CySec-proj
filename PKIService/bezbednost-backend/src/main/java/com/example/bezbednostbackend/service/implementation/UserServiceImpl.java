package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.dto.CombinedSearchDTO;
import com.example.bezbednostbackend.encryption.EncryptionService;
import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.model.Role;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EncryptionService encryptionService;
    @Override
    public User getById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) return user.get();
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if (user==null) {
            throw(new UsernameNotFoundException("User not found"));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(), true, true,
                true, user.getAuthorities());

    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public List<User> findAllByRole(Integer roleID) {
        return userRepository.findAllByRole(roleID);
    }

    @Override
    public void create(User user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        user.setAllowRefreshToken(true);
        user.setBlocked(false);
        user=encryptionService.encryptConfidentialUserData(user);
        userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(User employee) {
        userRepository.save(employee);
    }

    @Override
    public List<User> searchEngineers(CombinedSearchDTO dto){
        List<User> users = userRepository.combinedEngineerSearch(dto.getUsername(), dto.getName(), dto.getSurname());
        LocalDateTime formated = LocalDateTime.now().minusMonths(dto.getNumberOfMonthsEmployed());
        List<User> filteredUsers = users.stream().filter(u -> u.getStartOfEmployment().isBefore(formated)).collect(Collectors.toList());
        return filteredUsers;
    }

    @Override
    public void blockRefreshToken(User user){
        user.setAllowRefreshToken(false);
        userRepository.save(user);
    }

    @Override
    public void blockUser(User user){
        user.setBlocked(true);
        userRepository.save(user);
    }

}
