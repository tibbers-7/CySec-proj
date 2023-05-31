package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.model.Role;
import com.example.bezbednostbackend.repository.PrivilegeRepository;
import com.example.bezbednostbackend.repository.RoleRepository;
import com.example.bezbednostbackend.service.RolePrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return null;
    }

    @Override
    public void addPrivilegeToRole(Privilege privilege, Role role) {

    }

    @Override
    public void removePrivilegeFromRole(Privilege privilege, Role role) {

    }

    @Override
    public void createPrivilege(Privilege privilege) {

    }
}
