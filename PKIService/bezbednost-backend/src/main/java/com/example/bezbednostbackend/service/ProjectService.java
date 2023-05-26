package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.Address;
import com.example.bezbednostbackend.model.Project;

import java.util.Collection;
import java.util.Optional;

public interface ProjectService {
    Optional<Project> findById(Integer projectID);

    Collection<Project> findAll();

    Collection<Project> findAllByProjectManagerID(Integer projectManagerID);

    void create(Project project);

    void update(Project project);

    void delete(Integer id);
}
