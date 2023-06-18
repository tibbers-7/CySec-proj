package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Project;
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
public class ProjectDTO {
    private Integer id;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Start date is required")
    @Size(max = 100, message = "Start date cannot have more than a 100 characters")
    private String startDate;
    @NotEmpty(message = "End date is required")
    @Size(max = 100, message = "Start date cannot have more than a 100 characters")
    private String endDate;
    @NotEmpty(message = "Project manager id is required")
    private Integer projectManagerID;

    public ProjectDTO(Project dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.startDate = dto.getStartDate().toString();
        this.endDate = dto.getEndDate().toString();
        this.projectManagerID = dto.getProjectManagerID();
    }
}
