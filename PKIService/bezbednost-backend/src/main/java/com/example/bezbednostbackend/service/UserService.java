package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.dto.CombinedSearchDTO;
import com.example.bezbednostbackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User getById(Integer id);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Integer roleID);

    void create(User employee) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException;

    void delete(Integer id);

    void update(User employee);

    UserDetails loadUserByUsername(String username);

    void blockRefreshToken(User user);
    void blockUser(User user);

    List<User> searchEngineers(CombinedSearchDTO dto);
}
