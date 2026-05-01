package com.example.securityApp.SpringSecurity.entities;

import com.example.securityApp.SpringSecurity.entities.enums.Permission;
import com.example.securityApp.SpringSecurity.entities.enums.Role;
import com.example.securityApp.SpringSecurity.util.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static jakarta.persistence.EnumType.STRING;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    private Set<Permission> permissions;


    // Converts user roles and permissions into Spring Security granted authorities
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(
                role ->{
                    authorities.addAll(PermissionMapping.getAuthoritiesForRole(role));
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                });


        return authorities;

        /*
        // Adds the user's roles as granted authorities
        Set<SimpleGrantedAuthority> authorities =  roles.stream()
                .map(role-> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());

        // Adds the user's permissions as granted authorities
        permissions.forEach(
                permission -> authorities.add(new SimpleGrantedAuthority(permission.name()))
        );
*/


    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
