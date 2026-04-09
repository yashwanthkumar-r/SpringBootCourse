package com.example.securityApp.SpringSecurity.services;

import com.example.securityApp.SpringSecurity.dto.LoginDto;
import com.example.securityApp.SpringSecurity.entities.Users;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTServices jwtServices;

    public String loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        Users user = (Users) authentication.getPrincipal();
        return jwtServices.generateToken(user);
    }

}
