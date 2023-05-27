package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.ProjectWork;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectWorkDTO {

    private Integer id;
    private Integer projectID;
    private Integer engineerID;
    private String startDate;
    private String endDate;
    private String responsibility;

    public ProjectWorkDTO(ProjectWork dto){
        this.id = dto.getId();
        this.projectID = dto.getProjectID();
        this.engineerID = dto.getEngineerID();
        this.startDate = dto.getStartDate().toString();
        this.endDate = dto.getEndDate().toString();
        this.responsibility = dto.getResponsibility();
    }
}
