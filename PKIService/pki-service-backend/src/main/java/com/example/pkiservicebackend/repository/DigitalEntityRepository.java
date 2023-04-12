package com.example.pkiservicebackend.repository;

import com.example.pkiservicebackend.model.DigitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DigitalEntityRepository extends JpaRepository<DigitalEntity, Long> {

    @Query(value = "SELECT * FROM digital_entity dataa WHERE dataa.email = :email", nativeQuery = true)
    DigitalEntity findByEmail(String email);
}
