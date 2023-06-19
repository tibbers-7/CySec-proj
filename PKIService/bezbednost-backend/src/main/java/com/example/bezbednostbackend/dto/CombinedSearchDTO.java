package com.example.bezbednostbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinedSearchDTO {
    private String username;
    private String name;
    private String surname;
    private int numberOfMonthsEmployed;
}
