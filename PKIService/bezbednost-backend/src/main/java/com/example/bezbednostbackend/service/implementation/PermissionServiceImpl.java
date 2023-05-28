package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.Role;
import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.repository.PrivilegeRepository;
import com.example.bezbednostbackend.repository.RoleRepository;
import com.example.bezbednostbackend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Override
    public List<Privilege> getPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Privilege getPrivilege(String name) {
        return privilegeRepository.findByName(name);
    }

    @Override
    public Role getRole(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void createPrivilege(String privilegeName) {
        Privilege privilege= new Privilege(privilegeName);
        privilegeRepository.save(privilege);
    }

    @Override
    public void createRole(String roleName) {
        Role role=new Role(roleName);
    }

    @Override
    public void addPrivilegeToRole(String privilege, String role) {

    }

    @Override
    public void removePrivilegeFromRole(String privilege, String role) {

    }
}
