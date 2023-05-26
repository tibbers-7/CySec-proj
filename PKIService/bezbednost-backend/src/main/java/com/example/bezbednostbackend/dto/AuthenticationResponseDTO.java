package com.example.bezbednostbackend.dto;

import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="authentication_responses")
public class AuthenticationResponseDTO {
    private String accessToken;
    private String refreshToken;
}
