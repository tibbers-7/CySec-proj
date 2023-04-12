package com.example.pkiservicebackend.repository;

import com.example.pkiservicebackend.model.DigitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DigitalEntityRepository extends JpaRepository<DigitalEntity, Long> {

    @Query("Select u  from digital_entity u where u.email=?1")
    DigitalEntity findByEmail(String email);
}
