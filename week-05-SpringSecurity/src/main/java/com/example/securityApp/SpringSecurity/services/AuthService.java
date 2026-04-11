package com.example.securityApp.SpringSecurity.services;

import com.example.securityApp.SpringSecurity.dto.LoginDto;
import com.example.securityApp.SpringSecurity.dto.LoginResponseDto;
import com.example.securityApp.SpringSecurity.entities.Session;
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
    private final UserServiceImpl userService;
    private final SessionService sessionService;

    public LoginResponseDto loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        Users user = (Users) authentication.getPrincipal();
        String accessToken =  jwtServices.generateAccessToken(user);
        String refreshToken = jwtServices.generateRefreshToken(user);
        sessionService.createNewSession(user,refreshToken);
        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtServices.getUserFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        Users user = userService.getUserByID(userId);

        String accessToken =  jwtServices.generateAccessToken(user);
        return new LoginResponseDto(user.getId(),accessToken,refreshToken);

    }
}
