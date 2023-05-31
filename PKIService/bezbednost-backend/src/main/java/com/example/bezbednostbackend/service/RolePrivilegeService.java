package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.model.Role;

public interface RolePrivilegeService {

    public Role getRoleByName(String name);
    public void addPrivilegeToRole(Privilege privilege, Role role);
    public void removePrivilegeFromRole(Privilege privilege, Role role);
    public void createPrivilege(Privilege privilege);



}
