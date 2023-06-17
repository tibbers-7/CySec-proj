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
    
    @Query(value = "SELECT * FROM users JOIN users_roles ON id = user_id WHERE role_id = :roleID", nativeQuery = true)
    List<User> findAllByRole(@Param("roleID") Integer roleID);


    @Query(value = "SELECT * FROM users JOIN users_roles ON id = user_id WHERE role_id = 4", nativeQuery = true)
    List<User> combinedEngineerSearch(@Param("username") String username,
                                      @Param("name") String name,
                                      @Param("surname") String surname,
                                      @Param("startDate")LocalDateTime startOfEmployment, @Param("now") LocalDateTime now);
// and name LIKE '%:name%' and surname LIKE '%:surname%'
}

