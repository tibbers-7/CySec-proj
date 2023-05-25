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
@Table(name="authentication_responses")
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
