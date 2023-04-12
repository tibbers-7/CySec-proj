package com.example.pkiservicebackend.repository;

import com.example.pkiservicebackend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
