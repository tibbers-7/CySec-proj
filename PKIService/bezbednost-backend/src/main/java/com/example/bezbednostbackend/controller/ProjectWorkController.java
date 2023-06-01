package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.ProjectWorkDTO;
import com.example.bezbednostbackend.model.ProjectWork;
import com.example.bezbednostbackend.service.ProjectWorkService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/projectWork")
@CrossOrigin("http://localhost:4200")
public class ProjectWorkController {

    @Autowired
    private ProjectWorkService projectWorkService;

    @PreAuthorize("hasAuthority('GET_ALL_PROJECT_WORK_EXPERIENCES')")
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ProjectWorkDTO>> findAllProjectWorks(){
        Collection<ProjectWork> projectWorks = projectWorkService.findAll();
        Collection<ProjectWorkDTO> dtos = new ArrayList<ProjectWorkDTO>();
        for(ProjectWork projectWork: projectWorks){
            ProjectWorkDTO dto = new ProjectWorkDTO(projectWork);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_PROJECT_WORK_HISTORY_BY_ID')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectWorkDTO> findProjectWorkById(@PathVariable("id") Integer id){
        ProjectWork projectWork = projectWorkService.findById(id).orElse(null);
        if(projectWork == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProjectWorkDTO dto = new ProjectWorkDTO(projectWork);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_ALL_ENGINEERS_ON_PROJECT')")
    @GetMapping(value = "/findByProjectID/{projectID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ProjectWorkDTO>> findAllByProjectID(@PathVariable("projectID") Integer projectID){
        Collection<ProjectWork> projectWorks = projectWorkService.findAllByProjectID(projectID);
        Collection<ProjectWorkDTO> dtos = new ArrayList<ProjectWorkDTO>();
        for(ProjectWork projectWork: projectWorks){
            ProjectWorkDTO dto = new ProjectWorkDTO(projectWork);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    //svi projekti od inzenjera
    @PreAuthorize("hasAuthority('GET_ALL_PROJECTS_FOR_ENGINEER')")
    @GetMapping(value = "/findByEngineerID/{engineerID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ProjectWorkDTO>> findAllByEngineerID(@PathVariable("engineerID") Integer engineerID){
        Collection<ProjectWork> projectWorks = projectWorkService.findAllByEngineerID(engineerID);
        Collection<ProjectWorkDTO> dtos = new ArrayList<ProjectWorkDTO>();
        for(ProjectWork projectWork: projectWorks){
            ProjectWorkDTO dto = new ProjectWorkDTO(projectWork);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('CREATE_WORK_ON_PROJECT_FOR_ENGINEER')")
    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectWork> create(@RequestBody ProjectWorkDTO dto){
        ProjectWork projectWork = new ProjectWork();
        Map(dto, projectWork);
        try {
            //proveravamo da li je inzenjer vec na tom projektu
            Collection<ProjectWork> projects = projectWorkService.findAllByProjectID(projectWork.getProjectID());
            for(ProjectWork project: projects){
                if(project.getEngineerID() == projectWork.getEngineerID())
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            projectWorkService.create(projectWork);
            return new ResponseEntity<>(projectWork, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('UPDATE_WORK_ON_PROJECT_FOR_ENGINEER')")
    @PutMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody ProjectWorkDTO dto){
        ProjectWork projectWork = projectWorkService.findById(dto.getId()).orElse(null);
        if(projectWork == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map(dto, projectWork);
        projectWorkService.update(projectWork);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('DELETE_WORK_ON_PROJECT_FOR_ENGINEER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        ProjectWork projectWork = projectWorkService.findById(id).orElse(null);
        if(projectWork == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            projectWorkService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    private void Map(ProjectWorkDTO dto, ProjectWork projectWork){
        projectWork.setProjectID(dto.getProjectID());
        projectWork.setEngineerID(dto.getEngineerID());
        projectWork.setStartDate(LocalDate.parse(dto.getStartDate()));
        projectWork.setEndDate(LocalDate.parse(dto.getEndDate()));
        projectWork.setResponsibility(dto.getResponsibility());
    }
}
