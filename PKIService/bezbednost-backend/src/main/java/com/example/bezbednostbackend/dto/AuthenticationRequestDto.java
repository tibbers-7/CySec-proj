package com.example.bezbednostbackend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

// CREDENTIALS
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="authentication_requests")
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
