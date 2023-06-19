package com.example.bezbednostbackend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name="users")
@NamedNativeQuery(name = "findUsersByRole", query = "SELECT * FROM users JOIN users_roles ON id = user_id WHERE role_id = :roleID", resultClass = User.class)
@NamedNativeQuery(name = "combinedEngineerSearch",
        query = "SELECT * FROM users JOIN users_roles ON id = user_id WHERE role_id = 4 " +
                "AND lower(username) LIKE '%' || lower(:username) || '%' " +
                "AND lower(name) LIKE '%' || lower(:name) || '%' " +
                "AND lower(surname) LIKE '%' || lower(:surname) || '%'",
        resultClass = User.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String password;


    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


    private String workTitle;
    private boolean isActive;
    private boolean allowRefreshToken;
    private boolean isBlocked;
    private LocalDateTime startOfEmployment;

    public User(){
        super();
        this.isActive=false;
    }

    public User(String name, String surname, String username, String password, Address address, String phoneNumber, Role role, String workTitle, boolean isActive, LocalDateTime startOfEmployment) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
        var roles = new ArrayList<Role>();
        roles.add(role);
        this.roles = roles;
        this.workTitle = workTitle;
        this.isActive = isActive;
        this.startOfEmployment = startOfEmployment;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> allAuthorities = new ArrayList<>();
        for( Role role : getRoles()){
            allAuthorities.addAll(role.getPrivileges().stream().map(
                    privilege -> new SimpleGrantedAuthority(privilege.getName())).collect(Collectors.toList()));
        }
        return allAuthorities;
    }


    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword(){ return password; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
