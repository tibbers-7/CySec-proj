package com.example.bezbednostbackend.repository;


import com.example.bezbednostbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE role = :role", nativeQuery = true)
    List<User> findAllByRole(@Param("role") String role);

    @Query(value = "SELECT * FROM users WHERE role = ROLE_ENGINEER  and name = :name and surname = ", nativeQuery = true)
    List<User> combinedEngineerSearch(@Param("username") String username,
                                      @Param("name") String name,
                                      @Param("surname") String surname,
                                      @Param("startDate")LocalDateTime startOfEmployment, @Param("now") LocalDateTime now);


}

