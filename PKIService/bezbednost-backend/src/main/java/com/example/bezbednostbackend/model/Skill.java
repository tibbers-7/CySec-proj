package com.example.bezbednostbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="skills")
@NamedQuery(name="findSkillByEngineerId",query = "SELECT s FROM Skill s WHERE s.engineerID = :engineerID")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    private Integer engineerID;
    private String name;
    private Integer evaluation;

}
