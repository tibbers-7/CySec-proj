package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.ProjectDTO;
import com.example.bezbednostbackend.model.Project;
import com.example.bezbednostbackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_PROJECTS')")
    public ResponseEntity<Collection<ProjectDTO>> findProjects(){
        Collection<Project> projects = projectService.findAll();
        Collection<ProjectDTO> dtos = new ArrayList<ProjectDTO>();
        for(Project project: projects){
            ProjectDTO dto = new ProjectDTO(project);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    //MANAGER
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_PROJECT')")
    public ResponseEntity<ProjectDTO> findProjectById(@PathVariable("id") Integer id){
        Project project = projectService.findById(id).orElse(null);
        if(project == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProjectDTO dto = new ProjectDTO(project);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // MANAGER
    @GetMapping(value = "/findByProjectManager/{projectManagerID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_PROJECTS_MANAGER')")
    public ResponseEntity<Collection<ProjectDTO>> findProjectsByProjectManagerID(@PathVariable("projectManagerID") Integer projectManagerID){
        Collection<Project> projects = projectService.findAllByProjectManagerID(projectManagerID);
        Collection<ProjectDTO> dtos = new ArrayList<ProjectDTO>();
        for(Project project: projects){
            ProjectDTO dto = new ProjectDTO(project);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    //ADMIN
    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CREATE_PROJECT')")
    public ResponseEntity<Project> create(@RequestBody ProjectDTO dto){
        Project project = new Project();
        Map(dto, project);
        try{
            projectService.create(project);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('UPDATE_PROJECT')")
    public ResponseEntity<Void> update(@RequestBody ProjectDTO dto){
        Project project = projectService.findById(dto.getId()).orElse(null);
        if(project == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map(dto, project);
        projectService.update(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DELETE_PROJECT')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        Project project = projectService.findById(id).orElse(null);
        if(project == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            projectService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    private void Map(ProjectDTO dto, Project project){
        project.setName(dto.getName());
        project.setStartDate(LocalDate.parse(dto.getStartDate()));
        project.setEndDate(LocalDate.parse(dto.getEndDate()));
        project.setProjectManagerID(dto.getProjectManagerID());
    }
}
