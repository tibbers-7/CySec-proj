package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    @Valid
    @NotBlank(message = "Name cannot be empty or null")
    @Size(max = 100, message = "Name cannot have more than a 100 characters")
    private String name;

    @Valid
    @NotBlank(message = "Surname cannot be empty or null")
    @Size(max = 100, message = "Surname cannot have more than a 100 characters")
    private String surname;

    @Valid
    @NotBlank(message = "Username cannot be empty or null")
    @Email(message = "Username should be a valid email address.")
    @Size(max = 100, message = "Username cannot have more than a 100 characters")
    private String username;
//Minimum eight characters, at least one letter and one number
    @Valid
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password has to have at least one letter, one number and 8 characters at least")
    @NotBlank(message = "Password cannot be empty or null")
    private String password;

    @Valid
    @NotNull(message = "Address cannot be null")
    private Address address;

    @Valid
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;

    @Valid
    @Pattern(regexp = "ROLE_ENGINEER|ROLE_PROJECT_MANAGER|ROLE_HR_MANAGER", message = "User type can be either ROLE_ENGINEER,ROLE_HR_MANAGER or ROLE_PROJECT_MANAGER")
    @NotBlank(message = "Role cannot be empty")
    public String role;

    @Valid
    @NotBlank(message = "Work title cannot be empty")
    @Size(max = 100, message = "Work title cannot have more than a 100 characters")
    public String workTitle;

}
