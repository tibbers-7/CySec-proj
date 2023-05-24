package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.ProjectWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectWorkRepository extends JpaRepository<ProjectWork, Integer> {
}
