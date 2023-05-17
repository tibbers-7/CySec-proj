package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.model.Person;
import com.example.bezbednostbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value="/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> findAll(){
        List<Person> persons = personService.findAll();
        return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
    }

    @PostMapping(value="/addPerson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerson(@RequestBody Person newPerson){
        personService.create(newPerson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id){
        Person person = personService.findById(id).orElse(null);
        if(person == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            personService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
