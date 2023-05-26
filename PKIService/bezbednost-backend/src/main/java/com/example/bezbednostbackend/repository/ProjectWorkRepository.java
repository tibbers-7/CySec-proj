package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.ProjectWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectWorkRepository extends JpaRepository<ProjectWork, Integer> {

    @Query(value = "SELECT * FROM project_works WHERE engineerID = :engineerID", nativeQuery = true)
    List<ProjectWork> findAllByEngineerID(@Param("engineerID") Integer engineerID);

    @Query(value = "SELECT * FROM project_works WHERE projectID = :projectID", nativeQuery = true)
    List<ProjectWork> findAllByProjectID(@Param("projectID") Integer projectID);
}
