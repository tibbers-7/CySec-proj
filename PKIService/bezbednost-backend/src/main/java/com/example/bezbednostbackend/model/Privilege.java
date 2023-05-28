package com.example.bezbednostbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name="privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(String privilegeName) {
        this.name=privilegeName;
    }
}
