package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.model.Role;

import java.util.Collection;
import java.util.List;

public interface RolePrivilegeService {

    public Role getRoleByName(String name);
    public void addPrivilegeToRole(Privilege privilege, String roleName);
    public void removePrivilegeFromRole(Privilege privilege, String roleName);
    List<Privilege> getAllPrivileges();

    Collection<Privilege> getAllPrivilegesForEngineers();

    Collection<Privilege> getAllPrivilegesForHR();

    Collection<Privilege> getAllPrivilegesForPR();
}
