package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.Address;
import com.example.bezbednostbackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
