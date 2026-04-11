package com.example.securityApp.SpringSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private Long userId;
    private String accessToken;
    private String requestToken;
}
