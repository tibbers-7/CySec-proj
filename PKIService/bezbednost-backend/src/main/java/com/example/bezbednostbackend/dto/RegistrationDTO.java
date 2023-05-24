package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Address;

import javax.validation.constraints.*;

public class RegistrationDTO {

    @NotBlank(message = "Name cannot be empty or null")
    private String name;

    @NotBlank(message = "Surname cannot be empty or null")
    private String surname;

    @NotBlank(message = "Username cannot be empty or null")
    @Email(message = "Username should be valid")
    private String username;
//Minimum eight characters, at least one letter and one number
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password has to have at least one letter, one number and 8 characters at least")
    @NotBlank(message = "Password cannot be empty or null")
    private String password;
    @NotNull(message = "Address cannot be null")
    @NotEmpty(message = "Address cannot be empty")
    private Address address;

    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;

    @Pattern(regexp = "ENGINEER|PROJECT_MANAGER|HUMAN_RESOURCES_MANAGER", message = "Work titles can be either ENGINEER,HUMAN_RESOURCES_MANAGER or PROJECT_MANAGER")
    @NotBlank(message = "Work title cannot be empty")
    public String workTitle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }
}
