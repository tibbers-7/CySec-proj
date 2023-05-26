package com.example.bezbednostbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}