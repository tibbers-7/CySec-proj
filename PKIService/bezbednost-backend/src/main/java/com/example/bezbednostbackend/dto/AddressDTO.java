package com.example.bezbednostbackend.dto;

import com.example.bezbednostbackend.model.Address;
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
    private String country;
    private String city;
    private String streetAddress;

    public AddressDTO(Address dto){
        this.id = dto.getId();
        this.country = dto.getCountry();
        this.city = dto.getCity();
        this.streetAddress = dto.getStreetAddress();
    }

}
