package com.example.bezbednostbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="registration_requests")
@NamedQuery(name="findByUsername",query = "SELECT * FROM registration_requests WHERE username = :username ")
public class RegistrationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String password;
    @JdbcTypeCode(SqlTypes.JSON)
    private Address address;
    private String phoneNumber;
    private String role;
    private String workTitle;
    private LocalDateTime requestCreated;
    private LocalDateTime requestUpdated;
    private boolean isCancelled;
    private boolean isResolved;

    public RegistrationRequest(String name, String surname, String username, String password, Address address, String phoneNumber, String role, String workTitle, boolean isCancelled, boolean isResolved) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.workTitle = workTitle;
        this.requestCreated = LocalDateTime.now();
        this.isCancelled = isCancelled;
        this.isResolved = isResolved;
        this.requestUpdated = LocalDateTime.now();
    }


}
