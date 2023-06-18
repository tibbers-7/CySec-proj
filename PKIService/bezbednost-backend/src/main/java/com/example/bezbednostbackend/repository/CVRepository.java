package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CVRepository extends JpaRepository<CV, Integer> {
    @Query(value = "SELECT * FROM cvs WHERE engineerID = :engineerID", nativeQuery = true)
    Optional<CV> findByEngineerID(Integer engineerID);
}
