package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {
    @Query(value = "SELECT * FROM registration_requests WHERE username = :username ", nativeQuery = true)
    List<RegistrationRequest> findByUsername(@Param("username") String username);
}
