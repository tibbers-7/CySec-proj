package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.Address;
import com.example.bezbednostbackend.model.Skill;

import java.util.Collection;
import java.util.Optional;

public interface SkillService {
    Optional<Skill> findById(Integer skillID);

    Collection<Skill> findAll();

    Collection<Skill> findAllByEngineerID(Integer engineerID);

    void create(Skill skill);

    void update(Skill skill);

    void delete(Integer id);
}
