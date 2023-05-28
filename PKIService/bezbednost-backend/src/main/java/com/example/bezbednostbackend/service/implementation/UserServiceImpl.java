package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.Privilege;
import com.example.bezbednostbackend.model.Role;
import com.example.bezbednostbackend.model.User;
import com.example.bezbednostbackend.repository.PrivilegeRepository;
import com.example.bezbednostbackend.repository.RoleRepository;
import com.example.bezbednostbackend.repository.UserRepository;
import com.example.bezbednostbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
@Service @RequiredArgsConstructor @Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PrivilegeRepository privilegeRepository;
    @Override
    public User getById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) return user.get();
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        log.info("UserService: "+username);

        User user = userRepository.findByUsername(username);
        log.info("UserService: "+user.getRoles());
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true,
                    getAuthorities(user.getRoles())); //getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER")
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public List<User> findAllByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public void create(User employee) {
        userRepository.save(employee);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(User employee) {
        userRepository.save(employee);
    }
}
