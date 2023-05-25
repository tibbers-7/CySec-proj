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
    private String name;

    @Valid
    @NotBlank(message = "Surname cannot be empty or null")
    private String surname;

    @Valid
    @NotBlank(message = "Username cannot be empty or null")
    @Email(message = "Username should be valid")
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
    @Pattern(regexp = "ENGINEER|PROJECT_MANAGER|HR_MANAGER", message = "Work titles can be either ENGINEER,HR_MANAGER or PROJECT_MANAGER")
    @NotBlank(message = "Work title cannot be empty")
    public String workTitle;

}
