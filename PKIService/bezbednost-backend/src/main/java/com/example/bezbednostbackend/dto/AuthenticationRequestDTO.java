package com.example.bezbednostbackend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

// CREDENTIALS

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequestDTO {
    private String username;
    private String password;
}
