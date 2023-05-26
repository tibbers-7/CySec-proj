package com.example.bezbednostbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshRequestDTO {
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String accessToken;
}
