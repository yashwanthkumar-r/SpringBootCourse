package com.example.securityApp.SpringSecurity.controllers;

import com.example.securityApp.SpringSecurity.dto.LoginDto;
import com.example.securityApp.SpringSecurity.dto.LoginResponseDto;
import com.example.securityApp.SpringSecurity.dto.SignUpDto;
import com.example.securityApp.SpringSecurity.dto.UserDto;
import com.example.securityApp.SpringSecurity.services.AuthService;
import com.example.securityApp.SpringSecurity.services.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(userService.signUpUser(signUpDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginDto loginDto,
                                                      HttpServletResponse httpServletResponse){
        LoginResponseDto loginUser = authService.loginUser(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginUser.getRequestToken());
        cookie.setHttpOnly(true); //hide from JavaScript(avoid xss attacks)
        cookie.setSecure("production".equals(deployEnv)); //token only sent over HTTPS
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(loginUser);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(HttpServletRequest httpServletRequest){
        String refreshToken = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(()->new AuthenticationServiceException("Refresh token not found inside cookie"));

        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
