package com.example.bezbednostbackend.repository;


import com.example.bezbednostbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u FROM users u WHERE u.username = ?1", nativeQuery = true)
    User findByUsername(String username);

}

