package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Role;
import com.example.bezbednostbackend.model.User;
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
public class EmployeeDTO {

    private Integer id;
    @NotEmpty(message = "Name is required")
    @Size(max = 100, message = "Name cannot have more than a 100 characters")
    private String name;
    @NotEmpty(message = "Surname is required")
    @Size(max = 100, message = "Surname cannot have more than a 100 characters")
    private String surname;
    @NotEmpty(message = "Username is required")
    @Size(max = 100, message = "Username cannot have more than a 100 characters")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(max = 100, message = "Password cannot have more than a 100 characters")
    private String password;
    @NotEmpty(message = "Address id is required")
    private Integer addressID;
    @NotEmpty(message ="Phone number is required")
    @Size(max = 100, message = "Phone number cannot have more than a 100 characters")
    private String phoneNumber;
    @NotEmpty(message = "Role is required")
    @Size(max = 100, message = "Role cannot have more than a 100 characters")
    private String role;
    @NotEmpty(message = "Work title is required")
    @Size(max = 100, message = "Work title cannot have more than a 100 characters")
    private String workTitle;
    private String dateOfEmployment;

    private boolean isBlocked;
    private boolean allowRefreshToken;

    public EmployeeDTO(User dto){

        for(Role role : dto.getRoles()){
            this.role = role.getName();
        }
        this.id = dto.getId();
        this.name = dto.getName();
        this.surname = dto.getSurname();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.addressID = dto.getAddress().getId();
        this.phoneNumber = dto.getPhoneNumber();
        this.workTitle = dto.getWorkTitle();
        this.isBlocked=dto.isBlocked();
        this.allowRefreshToken=dto.isAllowRefreshToken();
        this.dateOfEmployment = dto.getStartOfEmployment().toString();
    }

}
