package com.example.bezbednostbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistrationApprovalDTO {
    private String approvalDescription;
    private Integer idOfRequest;

}