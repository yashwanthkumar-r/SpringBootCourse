package com.example.securityApp.SpringSecurity.dto;

import com.example.securityApp.SpringSecurity.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
   private String id;
   private String name;
   private String email;
   private Set<Role> roles;
}
