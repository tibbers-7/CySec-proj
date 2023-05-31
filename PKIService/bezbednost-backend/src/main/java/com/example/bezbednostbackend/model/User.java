package com.example.bezbednostbackend.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name="users")
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name="role_id")
    private Role role;
    private String workTitle;
    private boolean isActive;

    public User(){
        super();
        this.isActive=false;
    }

    public User(String name, String surname, String username, String password, Address address, String phoneNumber, Role role, String workTitle, boolean isActive) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.workTitle = workTitle;
        this.isActive = isActive;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
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
