package com.example.pkiservicebackend.model;


import jakarta.persistence.*;

@Entity
public class DigitalEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String organization;
    @Column
    private String organizationUnit;
    @Column
    private String country;
    @Column(unique = true)
    private String email;

    public DigitalEntity() {
    }

    public DigitalEntity(Long id, String firstName, String lastName, String organization, String organizationUnit, String country, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.country = country;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}