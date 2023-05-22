package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {
    @Query(value = "SELECT r FROM registration_requests r WHERE r.username = ?1", nativeQuery = true)
    RegistrationRequest findByUsername(String username);
}
