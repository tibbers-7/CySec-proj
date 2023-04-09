package com.example.pkiservicebackend.repository;

import com.example.pkiservicebackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
