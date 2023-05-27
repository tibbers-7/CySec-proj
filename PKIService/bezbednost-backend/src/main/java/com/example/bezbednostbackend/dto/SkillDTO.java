package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SkillDTO {
    private Integer id;
    private Integer engineerID;
    private String name;
    private Integer evaluation;

    public SkillDTO(Skill dto){
        this.id = dto.getId();
        this.engineerID = dto.getEngineerID();
        this.name = dto.getName();
        this.evaluation = dto.getEvaluation();
    }
}
