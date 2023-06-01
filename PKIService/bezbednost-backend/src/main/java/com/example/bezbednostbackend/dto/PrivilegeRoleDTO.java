package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Privilege;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrivilegeRoleDTO {
    private String roleName;
    private Privilege privilege;
}
