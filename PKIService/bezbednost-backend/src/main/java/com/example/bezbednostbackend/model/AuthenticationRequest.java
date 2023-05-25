package com.example.bezbednostbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="authentication_requests")
public class AuthenticationRequest {
    private String username;
    private String password;
}
