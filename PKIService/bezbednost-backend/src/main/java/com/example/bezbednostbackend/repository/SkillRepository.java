package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.ProjectWork;
import com.example.bezbednostbackend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    @Query(value = "SELECT * FROM skills WHERE engineerID = :engineerID", nativeQuery = true)
    List<Skill> findAllByEngineerID(@Param("engineerID") Integer engineerID);
}
