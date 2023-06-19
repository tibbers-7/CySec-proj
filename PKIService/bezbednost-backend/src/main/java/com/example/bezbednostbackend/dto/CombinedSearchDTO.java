package com.example.bezbednostbackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinedSearchDTO {
    @NotEmpty(message = "Username is required")
    @Size(max = 100, message = "Username cannot have more than a 100 characters")
    private String username;
    @NotEmpty(message = "Name is required")
    @Size(max = 100, message = "Name cannot have more than a 100 characters")
    private String name;
    @NotEmpty(message = "Surname is required")
    @Size(max = 100, message = "Surname cannot have more than a 100 characters")
    private String surname;
    @NotEmpty(message = "Start of employment is required")
    private int numberOfMonthsEmployed;
}
