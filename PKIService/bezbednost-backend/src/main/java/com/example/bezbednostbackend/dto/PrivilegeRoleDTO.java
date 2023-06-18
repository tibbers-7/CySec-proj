package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Privilege;
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
public class PrivilegeRoleDTO {
    @NotEmpty(message = "Role name is required")
    @Size(max = 100, message = "Role cannot have more than a 100 characters")
    private String roleName;
    @NotEmpty(message = "Privilege is required")
    @Size(max = 100, message = "Privilege cannot have more than a 100 characters")
    private Privilege privilege;
}
