package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.Project;
import com.example.bezbednostbackend.repository.ProjectRepository;
import com.example.bezbednostbackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Optional<Project> findById(Integer projectID) {
        return projectRepository.findById(projectID);
    }

    @Override
    public Collection<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Collection<Project> findAllByProjectManagerID(Integer projectManagerID) {
        return projectRepository.findAllByProjectManagerID(projectManagerID);
    }

    @Override
    public void create(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void update(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }
}
