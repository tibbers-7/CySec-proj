package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.model.Role;
import com.example.bezbednostbackend.repository.PrivilegeRepository;
import com.example.bezbednostbackend.repository.RoleRepository;
import com.example.bezbednostbackend.service.RolePrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        if(role.isPresent()) return role.get();
        return null;
    }

    @Override
    public void addPrivilegeToRole(Privilege privilege, String roleName) {
        Role role = getRoleByName(roleName);
        if(role == null) return;
        Collection<Privilege> privileges = role.getPrivileges();
        if(!privileges.contains(privilege)) {
            privileges.add(privilege);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }

    @Override
    public void removePrivilegeFromRole(Privilege privilege, String roleName) {
        Role role = getRoleByName(roleName);
        if(role == null) return;
        Collection<Privilege> privileges = role.getPrivileges();
        if(privileges.contains(privilege)) {
            privileges.remove(privilege);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }

    @Override
    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public Collection<Privilege> getAllPrivilegesForEngineers() {
        Role role = getRoleByName("ROLE_ENGINEER");
        if(role != null) return role.getPrivileges();
        return new ArrayList<>();
    }

    @Override
    public Collection<Privilege> getAllPrivilegesForHR() {
        Role role = getRoleByName("ROLE_HR_MANAGER");
        if(role != null) return role.getPrivileges();
        return new ArrayList<>();
    }

    @Override
    public Collection<Privilege> getAllPrivilegesForPR() {
        Role role = getRoleByName("ROLE_PROJECT_MANAGER");
        if(role != null) return role.getPrivileges();
        return new ArrayList<>();
    }

}
