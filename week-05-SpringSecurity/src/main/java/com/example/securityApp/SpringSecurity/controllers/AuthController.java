package com.example.securityApp.SpringSecurity.controllers;

import com.example.securityApp.SpringSecurity.dto.LoginDto;
import com.example.securityApp.SpringSecurity.dto.SignUpDto;
import com.example.securityApp.SpringSecurity.dto.UserDto;
import com.example.securityApp.SpringSecurity.services.AuthService;
import com.example.securityApp.SpringSecurity.services.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(userService.signUpUser(signUpDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        String token = authService.loginUser(loginDto);

        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(token);
    }
}
