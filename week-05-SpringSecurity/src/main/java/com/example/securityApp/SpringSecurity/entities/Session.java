package com.example.securityApp.SpringSecurity.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SessionId;

    private String refreshToken;

    @UpdateTimestamp
    private LocalDateTime lastUsedAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Users user;
}
