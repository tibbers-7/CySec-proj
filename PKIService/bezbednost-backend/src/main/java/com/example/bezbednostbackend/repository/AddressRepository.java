package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
