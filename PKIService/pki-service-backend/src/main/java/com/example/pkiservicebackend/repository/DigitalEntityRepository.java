package com.example.pkiservicebackend.repository;

import com.example.pkiservicebackend.model.DigitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface DigitalEntityRepository extends JpaRepository<DigitalEntity, Long> {

    @Query(value = "SELECT * FROM digital_entity data WHERE data.email = :email", nativeQuery = true)
    DigitalEntity findByEmail(String email);

    @Query(value = "SELECT * FROM digital_entity data " +
            "WHERE data.id = ANY(SELECT subject_id FROM certificate_data WHERE " +
            "(certificate_role = 'SELF_SIGNED' OR certificate_role = 'INTERMEDIATE') " +
            "AND certificate_status = 'VALID')", nativeQuery = true)
    Collection<DigitalEntity> getSSAndCA();
}
