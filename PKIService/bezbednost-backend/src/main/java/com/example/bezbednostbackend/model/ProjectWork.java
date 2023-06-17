package com.example.bezbednostbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="projectWorks")
@NamedQuery(name="findByEngineer",query = "SELECT pw FROM ProjectWork pw WHERE pw.engineerID = :engineerID")
@NamedQuery(name="findPWByProject",query = "SELECT pw FROM ProjectWork pw WHERE pw.projectID = :projectId")

public class ProjectWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    private Integer projectID;
    private Integer engineerID;
    private LocalDate startDate;
    private LocalDate endDate;
    private String responsibility;


}
