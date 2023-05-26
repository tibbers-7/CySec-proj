package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT * FROM projects WHERE project_ManagerID = :project_ManagerID", nativeQuery = true)
    List<Project> findAllByProjectManagerID(@Param("project_ManagerID") Integer projectManagerID);
}
