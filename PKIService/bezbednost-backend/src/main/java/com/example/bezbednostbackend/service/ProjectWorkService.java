package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.ProjectWork;

import java.util.Collection;
import java.util.Optional;

public interface ProjectWorkService {
    Optional<ProjectWork> findById(Integer projectWorkID);
    Collection<ProjectWork> findAll();
    Collection<ProjectWork> findAllByEngineerID(Integer engineerID);
    Collection<ProjectWork> findAllByProjectID(Integer projectID);
    void create(ProjectWork projectWork);
    void update(ProjectWork projectWork);
    void delete(Integer id);
}
