package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.ProjectWork;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "Project id is required")
    private Integer projectID;
    @NotEmpty(message = "Engineer id is required")
    private Integer engineerID;
    @NotEmpty(message = "Start date is required")
    @Size(max = 100, message = "Start date cannot have more than a 100 characters")
    private String startDate;
    @NotEmpty(message = "End date is required")
    @Size(max = 100, message = "End date cannot have more than a 100 characters")
    private String endDate;
    @NotEmpty(message = "Responsibility is required")
    @Size(max = 100, message = "Responsibility cannot have more than a 100 characters")
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
