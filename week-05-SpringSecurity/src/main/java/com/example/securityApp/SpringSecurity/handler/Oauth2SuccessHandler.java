package com.example.securityApp.SpringSecurity.handler;

import com.example.securityApp.SpringSecurity.entities.Users;
import com.example.securityApp.SpringSecurity.services.JWTServices;
import com.example.securityApp.SpringSecurity.services.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor

public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserServiceImpl userService;
    private final JWTServices jwtServices;

    @Value("${deploy.env}")
    private String deployEnv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        //log.info(oAuth2User.getAttribute("email"));

        String email = oAuth2User.getAttribute("email");

        Users user =  userService.getUserByEmail(email);
        if(user==null){
            Users newUser = Users.builder()
                    .name(oAuth2User.getName())
                    .email(email)
                    .build();

            user = userService.save(newUser);
            String accessToken =  jwtServices.generateAccessToken(user);
            String refreshToken = jwtServices.generateRefreshToken(user);

            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true); //hide from JavaScript(avoid xss attacks)
            cookie.setSecure("production".equals(deployEnv)); //token only sent over HTTPS
            response.addCookie(cookie);

            String frontEndUrl = "http://localhost:9000/home.html?token="+accessToken;

            //getRedirectStrategy().sendRedirect(request,response,frontEndUrl);
            response.sendRedirect(frontEndUrl);
        }

    }
}
