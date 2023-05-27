package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.Address;
import com.example.bezbednostbackend.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
}
