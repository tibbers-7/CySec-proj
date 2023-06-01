package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.AddressDTO;
import com.example.bezbednostbackend.model.Address;
import com.example.bezbednostbackend.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AddressController {

    @Autowired
    private final AddressService addressService;

    @PreAuthorize("hasAuthority('GET_ADDRESSES')")
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AddressDTO>> findAddresses() {
        Collection<Address> addresses = addressService.findAll();
        Collection<AddressDTO> dtos = new ArrayList<AddressDTO>();
        for(Address address : addresses){
            AddressDTO dto = new AddressDTO(address);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_ADDRESS_BY_ID')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDTO> findAddressById(@PathVariable("id") Integer id) {
        Address address = addressService.findById(id).orElse(null);
        if(address == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AddressDTO dto = new AddressDTO(address);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('CREATE_ADDRESS')")
    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> create(@RequestBody AddressDTO dto)  {
        Address address = new Address();
        Map(dto, address);
        try {
            addressService.create(address);
            return new ResponseEntity<>(address,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('UPDATE_ADDRESS')")
    @PutMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody AddressDTO dto)  {
        Address address = addressService.findById(dto.getId()).orElse(null);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map(dto, address);

        addressService.update(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_ADDRESS')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Address address = addressService.findById(id).orElse(null);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } else{
            addressService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private void Map(AddressDTO dto, Address address){
        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setStreetAddress(dto.getStreetAddress());
    }

}
