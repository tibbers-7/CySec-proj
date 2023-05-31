package com.example.bezbednostbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationResolveRequestDTO {
    private String reasoning;
    private Integer idOfRequest;
}


