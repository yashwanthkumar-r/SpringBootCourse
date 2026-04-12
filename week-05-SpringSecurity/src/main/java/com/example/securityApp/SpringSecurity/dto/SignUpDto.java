package com.example.securityApp.SpringSecurity.dto;

import com.example.securityApp.SpringSecurity.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;


}
