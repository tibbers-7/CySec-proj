package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.EmployeeDTO;
import com.example.bezbednostbackend.dto.RegistrationApprovalDTO;
import com.example.bezbednostbackend.dto.RegistrationCancellationDTO;
import com.example.bezbednostbackend.dto.StringResponseDTO;
import com.example.bezbednostbackend.enums.Role;
import com.example.bezbednostbackend.model.Address;
import com.example.bezbednostbackend.model.RegistrationRequest;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.service.AddressService;
import com.example.bezbednostbackend.service.AuthenticationService;
import com.example.bezbednostbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class UserController{

@Autowired
private final UserService userService;
@Autowired
private final AuthenticationService authenticationService;
@Autowired
private final AddressService addressService;

@Autowired
private final PasswordEncoder passwordEncoder;



    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //ovo samo admin moze
    @PostMapping(value="/register/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> cancelRegistration(@RequestBody RegistrationCancellationDTO dto) {
        //treba resiti cist nacin na koji ce se vratiti da li je uspesno ili ne
        authenticationService.cancelRegistrationRequest(dto);
        return new ResponseEntity<>(new StringResponseDTO("request cancelled"), HttpStatus.OK);
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EmployeeDTO dto = new EmployeeDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

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

    @GetMapping(value = "/findByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> findEmployeeByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username).orElse(null);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EmployeeDTO dto = new EmployeeDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value="/engineers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EmployeeDTO>> findAllEngineers(){
        Collection<User> engineers = userService.findAllByRole("ENGINEER");
        Collection<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
        for(User engineer: engineers){
            EmployeeDTO dto = new EmployeeDTO(engineer);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value="/projectManagers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EmployeeDTO>> findAllProjectManagers(){
        Collection<User> managers = userService.findAllByRole("PROJECT_MANAGER");
        Collection<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
        for(User manager: managers){
            EmployeeDTO dto = new EmployeeDTO(manager);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody EmployeeDTO dto){
        User user = new User();
        Map(dto, user);
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

    @PutMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody EmployeeDTO dto){
        User user = userService.getById(dto.getId());
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map(dto, user);
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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

    private void Map(EmployeeDTO dto, User user) {
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setActive(true);
        user.setWorkTitle(dto.getWorkTitle());
        Address address = addressService.findById(dto.getAddressID()).orElse(null);
        if (address != null) {
            user.setAddress(address);
        }
    }

    //ovo samo admin moze
    @PostMapping(value="/register/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> approveRegistration(@RequestBody RegistrationApprovalDTO dto, HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeyException {
        //treba resiti cist nacin na koji ce se vratiti da li je uspesno ili ne
        authenticationService.approveRegistrationRequest(dto);

        return new ResponseEntity<>(new StringResponseDTO("request approved"), HttpStatus.OK);
    }

   @GetMapping(value="/registration-requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegistrationRequest>> getAllRequests(){
        List<RegistrationRequest> requests = authenticationService.getAllRegistrationRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
   }


}