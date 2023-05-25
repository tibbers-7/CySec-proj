package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.Address;

import java.util.Collection;
import java.util.Optional;

public interface AddressService {
    Optional<Address> findById(Integer addressID);

    Collection<Address> findAll();

    void create(Address address);

    void update(Address address);

    void delete(Integer id);
}
