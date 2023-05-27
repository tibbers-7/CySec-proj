package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.Skill;
import com.example.bezbednostbackend.repository.SkillRepository;
import com.example.bezbednostbackend.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Optional<Skill> findById(Integer skillID) {
        return skillRepository.findById(skillID);
    }

    @Override
    public Collection<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public Collection<Skill> findAllByEngineerID(Integer engineerID) {
        return skillRepository.findAllByEngineerID(engineerID);
    }

    @Override
    public void create(Skill skill) {
        skillRepository.save(skill);
    }

    @Override
    public void update(Skill skill) {
        skillRepository.save(skill);
    }

    @Override
    public void delete(Integer id) {
        skillRepository.deleteById(id);
    }
}
