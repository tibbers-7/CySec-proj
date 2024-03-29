package com.example.pkiservicebackend.service;

import com.example.pkiservicebackend.model.Person;
import com.example.pkiservicebackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Optional<Person> findById(Integer id){
        return personRepository.findById(id);
    }

    public Person create(Person newPerson){
        return personRepository.save(newPerson);
    }

    public void delete(Integer id){
        personRepository.deleteById(id);
    }

}
