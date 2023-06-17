package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.CombinedSearchDTO;
import com.example.bezbednostbackend.dto.EmployeeDTO;
import com.example.bezbednostbackend.dto.RegistrationResolveRequestDTO;
import com.example.bezbednostbackend.dto.StringResponseDTO;
import com.example.bezbednostbackend.model.Address;
import com.example.bezbednostbackend.model.RegistrationRequest;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.service.AddressService;
import com.example.bezbednostbackend.service.AuthenticationService;
import com.example.bezbednostbackend.service.RolePrivilegeService;
import com.example.bezbednostbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class UserController{

@Autowired
private final UserService userService;
@Autowired
private final AuthenticationService authenticationService;
@Autowired
private final AddressService addressService;
@Autowired
private final PasswordEncoder passwordEncoder;
@Autowired
private final RolePrivilegeService rolePrivilegeService;


    @PreAuthorize("hasAuthority('GET_USER_BY_ID')")
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CANCEL_REGISTRATION_REQUEST')")
    @PostMapping(value="/register/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> cancelRegistration(@RequestBody RegistrationResolveRequestDTO dto) {
        //treba resiti cist nacin na koji ce se vratiti da li je uspesno ili ne
        authenticationService.cancelRegistrationRequest(dto);
        return new ResponseEntity<>(new StringResponseDTO("request cancelled"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_EMPLOYEE_BY_ID')")
    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EmployeeDTO dto = new EmployeeDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_ALL_EMPLOYEES')")
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EmployeeDTO>> findAllEmployees(){
        Collection<User> users = userService.findAll();
        Collection<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
        for(User user: users){
            EmployeeDTO dto = new EmployeeDTO(user);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_EMPLOYEE_BY_USERNAME')")
    @GetMapping(value = "/findByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> findEmployeeByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username).orElse(null);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EmployeeDTO dto = new EmployeeDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_ALL_ENGINEERS')")
    @GetMapping(value="/engineers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EmployeeDTO>> findAllEngineers(){
        //u tabeli roles ROLE_ENGINEER ima id 4
        Collection<User> engineers = userService.findAllByRole(4);
        Collection<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
        for(User engineer: engineers){
            EmployeeDTO dto = new EmployeeDTO(engineer);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GET_ALL_PROJECT_MANAGERS')")
    @GetMapping(value="/projectManagers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EmployeeDTO>> findAllProjectManagers(){
        //u tabeli roles ROLE_PROJECT_MANAGER ima id 2
        Collection<User> managers = userService.findAllByRole(2);
        Collection<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
        for(User manager: managers){
            EmployeeDTO dto = new EmployeeDTO(manager);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_EMPLOYEE')")
    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody EmployeeDTO dto){
        User user = new User();
        user = Map(dto, user);
        try{
            User userOld = userService.findByUsername(dto.getUsername()).orElse(null);
            if(userOld == null){
                userService.create(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('UPDATE_EMPLOYEE')")
    @PutMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody EmployeeDTO dto){
        User user = userService.getById(dto.getId());
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //user = Map(dto, user);
        userService.update(Map(dto, user));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    private User Map(EmployeeDTO dto, User user) {
        return new User(dto.getName(), dto.getSurname(), dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()), addressService.findById(dto.getAddressID()).orElse(null), dto.getPhoneNumber(),
                rolePrivilegeService.getRoleByName(dto.getRole()), dto.getWorkTitle(), true);

    }

    @PreAuthorize("hasAuthority('APPROVE_REGISTRATION_REQUEST')")
    @PostMapping(value="/register/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> approveRegistration(@RequestBody RegistrationResolveRequestDTO dto) throws NoSuchAlgorithmException, InvalidKeyException {
        //treba resiti cist nacin na koji ce se vratiti da li je uspesno ili ne
        authenticationService.approveRegistrationRequest(dto);

        return new ResponseEntity<>(new StringResponseDTO("request approved"), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('GET_ALL_REGISTRATION_REQUESTS')")
    @GetMapping(value="/registration-requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegistrationRequest>> getAllRequests(){
        List<RegistrationRequest> requests = authenticationService.getAllRegistrationRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
   }

   /*
   @PreAuthorize("hasAuthority('SEARCH_ENGINEERS')")
   @PostMapping(value="/search-engineers", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Collection<User>> searchEngineers(@RequestBody CombinedSearchDTO dto){
        List<User> engineers = userService.searchEngineers(dto);
        return new ResponseEntity<>(engineers, HttpStatus.OK);
   }*/

    @PreAuthorize("hasAuthority('BLOCK_USER')")
    @PostMapping(value="/block-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> blockUser(@RequestBody EmployeeDTO dto){
        User user = userService.getById(dto.getId());
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.blockUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('BLOCK_REFRESH_TOKEN')")
    @PostMapping(value="/block-refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> blockRefreshToken(@RequestBody EmployeeDTO dto){
        User user = userService.getById(dto.getId());
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.blockRefreshToken(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}