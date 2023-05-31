package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.ProjectWork;
import com.example.bezbednostbackend.repository.ProjectWorkRepository;
import com.example.bezbednostbackend.service.ProjectWorkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectWorkServiceImpl implements ProjectWorkService {

    @Autowired
    private final ProjectWorkRepository projectWorkRepository;

    @Override
    public Optional<ProjectWork> findById(Integer projectWorkID) {
        return projectWorkRepository.findById(projectWorkID);
    }

    @Override
    public Collection<ProjectWork> findAll() {
        return projectWorkRepository.findAll();
    }

    @Override
    public Collection<ProjectWork> findAllByEngineerID(Integer engineerID) {
        return projectWorkRepository.findAllByEngineerID(engineerID);
    }

    @Override
    public Collection<ProjectWork> findAllByProjectID(Integer projectID) {
        return projectWorkRepository.findAllByProjectID(projectID);
    }

    @Override
    public void create(ProjectWork projectWork) {
        projectWorkRepository.save(projectWork);
    }

    @Override
    public void update(ProjectWork projectWork) {
        projectWorkRepository.save(projectWork);
    }

    @Override
    public void delete(Integer id) {
        projectWorkRepository.deleteById(id);
    }
}
