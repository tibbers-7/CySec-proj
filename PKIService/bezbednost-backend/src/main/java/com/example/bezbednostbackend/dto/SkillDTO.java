package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Skill;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Engineer id is required")
    private Integer engineerID;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Evaluation is required")
    private Integer evaluation;

    public SkillDTO(Skill dto){
        this.id = dto.getId();
        this.engineerID = dto.getEngineerID();
        this.name = dto.getName();
        this.evaluation = dto.getEvaluation();
    }
}
