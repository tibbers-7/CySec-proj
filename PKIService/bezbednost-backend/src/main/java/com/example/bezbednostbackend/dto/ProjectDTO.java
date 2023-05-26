package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectDTO {
    private Integer id;
    private String name;
    private String startDate;
    private String endDate;
    private Integer projectManagerID;

    public ProjectDTO(Project dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.startDate = dto.getStartDate().toString();
        this.endDate = dto.getEndDate().toString();
        this.projectManagerID = dto.getProjectManagerID();
    }
}
