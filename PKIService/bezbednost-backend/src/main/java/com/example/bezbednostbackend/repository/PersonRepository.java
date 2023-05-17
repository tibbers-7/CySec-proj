package com.example.bezbednostbackend.repository;

import com.example.bezbednostbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
