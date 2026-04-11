package com.example.securityApp.SpringSecurity.repository;

import com.example.securityApp.SpringSecurity.entities.Session;
import com.example.securityApp.SpringSecurity.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {

    @Query("Select COUNT(s) from Session s where s.refreshToken= :refreshToken")
    Integer getSessionCount(@Param("refreshToken") String refreshToken);

    Optional<Session> findByRefreshToken(String refreshToken);

    List<Session> findByUser(Users user);
}
