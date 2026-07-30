package com.codingshuttle.week_09_learn_spring_ai.dto;

import com.codingshuttle.week_09_learn_spring_ai.entity.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@AllArgsConstructor
public class BookingResponse {

    private Long id;

    private String userId;

    private String destination;

    private Instant departureTime;

    private BookingStatus bookingStatus;

    Instant bookedAt;
}
