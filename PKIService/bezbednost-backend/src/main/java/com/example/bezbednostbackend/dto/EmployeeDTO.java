package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.User;
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
    private String name;
    private String surname;
    private String username;
    private String password;
    private Integer addressID;
    private String phoneNumber;
    private String role;

    public EmployeeDTO(User dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.surname = dto.getSurname();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.addressID = dto.getAddress().getId();
        this.phoneNumber = dto.getPhoneNumber();
        //TODO
        //this.role = String.valueOf(dto.getRole());
    }

}
