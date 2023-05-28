package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.SkillDTO;
import com.example.bezbednostbackend.model.Skill;
import com.example.bezbednostbackend.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_SKILLS')")
    public ResponseEntity<Collection<SkillDTO>> findSkills(){
        Collection<Skill> skills = skillService.findAll();
        Collection<SkillDTO> dtos = new ArrayList<SkillDTO>();
        for(Skill skill: skills){
            SkillDTO dto = new SkillDTO(skill);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_SKILL')")
    public ResponseEntity<SkillDTO> findSkillById(@PathVariable("id") Integer id){
        Skill skill = skillService.findById(id).orElse(null);
        if(skill == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SkillDTO dto = new SkillDTO(skill);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/findByEngineerID/{engineerID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_SKILLS_ENGINEER')")
    public ResponseEntity<Collection<SkillDTO>> findAllByEngineerID(@PathVariable("engineerID") Integer engineerID){
        Collection<Skill> skills = skillService.findAllByEngineerID(engineerID);
        Collection<SkillDTO> dtos = new ArrayList<SkillDTO>();
        for(Skill skill: skills){
            SkillDTO dto = new SkillDTO(skill);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADD_SKILL_ENGINEER')")
    public ResponseEntity<Skill> create(@RequestBody SkillDTO dto){
        Skill skill = new Skill();
        Map(dto, skill);
        try{
            //proveravamo da li inzenjer vec ima tu vestinu
            Collection<Skill> skills = skillService.findAllByEngineerID(skill.getEngineerID());
            for(Skill skill1: skills){
                if(skill1.getName().equalsIgnoreCase(skill.getName()))
                    return new ResponseEntity<>(HttpStatus.CONFLICT);

            }
            skillService.create(skill);
            return new ResponseEntity<>(skill,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('UPDATE_SKILL')")
    public ResponseEntity<Void> update(@RequestBody SkillDTO dto){
        Skill skill = skillService.findById(dto.getId()).orElse(null);
        if(skill == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map(dto, skill);
        skillService.update(skill);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DELETE_SKILL')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        Skill skill = skillService.findById(id).orElse(null);
        if(skill == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            skillService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private void Map(SkillDTO dto, Skill skill){
        skill.setEngineerID(dto.getEngineerID());
        skill.setName(dto.getName());
        skill.setEvaluation(dto.getEvaluation());
    }
}
