package com.codingshuttle.week_09_learn_spring_ai.repository;

import com.codingshuttle.week_09_learn_spring_ai.entity.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface FlightBookingRepository extends JpaRepository<FlightBooking,Long> {

    List<FlightBooking> findByUserIdOrderByDepartureTimeDesc(String userId);

    boolean existsByUserIdAndDestinationAndDepartureTime(String userId,
                                                         String destination, Instant departureTime);
}