package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Address;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {

    private Integer id;
    @NotEmpty(message = "Country is required")
    @Size(max = 100, message = "Country cannot have more than a 100 characters")
    private String country;
    @NotEmpty(message = "City is required")
    @Size(max = 100, message = "City cannot have more than a 100 characters")
    private String city;
    @NotEmpty(message = "Street address is required")
    @Size(max = 100, message = "Street address cannot have more than a 100 characters")
    private String streetAddress;

    public AddressDTO(Address dto){
        this.id = dto.getId();
        this.country = dto.getCountry();
        this.city = dto.getCity();
        this.streetAddress = dto.getStreetAddress();
    }

}
