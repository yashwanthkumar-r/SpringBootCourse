package com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"patient","doctor"})
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    private String status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient; //owning side

    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor; //owning side
}
