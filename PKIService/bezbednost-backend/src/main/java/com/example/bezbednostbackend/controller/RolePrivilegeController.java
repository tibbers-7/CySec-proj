package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.PrivilegeRoleDTO;
import com.example.bezbednostbackend.dto.RegistrationResolveRequestDTO;
import com.example.bezbednostbackend.dto.StringResponseDTO;
import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.service.RolePrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/privilege")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class RolePrivilegeController {


    private final RolePrivilegeService rolePrivilegeService;


    @PreAuthorize("hasAuthority('ADD_PRIVILEGE_TO_ROLE')")
    @PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> addPrivilegeToRole(@RequestBody PrivilegeRoleDTO dto) {
        rolePrivilegeService.addPrivilegeToRole(dto.getPrivilege(), dto.getRoleName());
        return new ResponseEntity<>(new StringResponseDTO("completed adding privilege"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('REMOVE_PRIVILEGE_FROM_ROLE')")
    @PostMapping(value="/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> removePrivilegeFromRole(@RequestBody PrivilegeRoleDTO dto) {
    rolePrivilegeService.removePrivilegeFromRole(dto.getPrivilege(), dto.getRoleName());
        return new ResponseEntity<>(new StringResponseDTO("completed removing privilege"), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('GET_ALL_PRIVILEGES')")
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Privilege>> getAllPrivileges() {
       var privileges = rolePrivilegeService.getAllPrivileges();
       if(privileges.isEmpty()) privileges = new ArrayList<>();
        return new ResponseEntity<>(privileges, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRIVILEGES_FOR_ENGINEER')")
    @GetMapping(value="/engineer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Privilege>> getAllPrivilegesForEngineers() {
        var privileges = rolePrivilegeService.getAllPrivilegesForEngineers();
        if(privileges.isEmpty()) privileges = new ArrayList<>();
        return new ResponseEntity<>(privileges, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRIVILEGES_FOR_HR')")
    @GetMapping(value="/HR", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Privilege>> getAllPrivilegesForHR() {
        var privileges = rolePrivilegeService.getAllPrivilegesForHR();
        if(privileges.isEmpty()) privileges = new ArrayList<>();
        return new ResponseEntity<>(privileges, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRIVILEGES_FOR_PR')")
    @GetMapping(value="/PR", produces  = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Privilege>>getAllPrivilegesForPR() {
        var privileges = rolePrivilegeService.getAllPrivilegesForPR();
        if(privileges.isEmpty()) privileges = new ArrayList<>();
        return new ResponseEntity<>(privileges, HttpStatus.OK);
    }
}
