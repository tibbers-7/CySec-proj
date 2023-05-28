package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.model.CV;
import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private PermissionService permissionService;
    @GetMapping(value="/")
    @PreAuthorize("hasAuthority('GET_PRIVILEGES')")
    public List<Privilege> getPrivileges(){
        return permissionService.getPrivileges();
    }
}
