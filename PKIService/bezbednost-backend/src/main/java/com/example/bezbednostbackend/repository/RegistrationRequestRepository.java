package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {
    @Query(name="FindRRByUsername")
    List<RegistrationRequest> findByUsername(@Param("username") String username);
}
