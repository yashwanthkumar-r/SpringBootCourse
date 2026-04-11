package com.example.securityApp.SpringSecurity.services;

import com.example.securityApp.SpringSecurity.entities.Session;
import com.example.securityApp.SpringSecurity.entities.Users;
import com.example.securityApp.SpringSecurity.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final Integer SESSION_LIMIT = 2;

    public Session createNewSession(Users user, String refreshToken){

        List<Session> sessions = sessionRepository.findByUser(user);

        if(sessions.size()==2){
            sessions.sort(Comparator.comparing(session -> session.getLastUsedAt()));
            sessionRepository.delete(sessions.getFirst());
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

       return  sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new AuthenticationServiceException("Resource Not found: " + refreshToken));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
