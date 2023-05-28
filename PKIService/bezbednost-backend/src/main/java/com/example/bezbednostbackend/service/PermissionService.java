package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.Role;
import com.example.bezbednostbackend.model.Privilege;

import java.util.List;

public interface PermissionService {

    List<Privilege> getPrivileges();
    List<Role> getRoles();
    Privilege getPrivilege(String name);
    Role getRole(String name);
    void createPrivilege(String privilege);
    void createRole(String role);
    void addPrivilegeToRole(String privilege, String role);
    void removePrivilegeFromRole(String privilege, String role);
}
