package com.codingshuttle.week_09_learn_spring_ai.service;

import com.codingshuttle.week_09_learn_spring_ai.entity.BookingStatus;
import com.codingshuttle.week_09_learn_spring_ai.entity.FlightBooking;
import com.codingshuttle.week_09_learn_spring_ai.repository.FlightBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightBookingService {

    private final FlightBookingRepository flightBookingRepository;

    public FlightBooking createBooking(String userId, String destination, Instant departureTime){
        boolean exists = flightBookingRepository.existsByUserIdAndDestinationAndDepartureTime(
                userId, destination, departureTime);

        if(exists){
            throw new IllegalArgumentException(
                    "You already have a booking to "+ destination + "on that date.");
        }

        FlightBooking flightBooking = FlightBooking.builder()
                .userId(userId)
                .destination(destination)
                .departureTime(departureTime)
                .bookingStatus(BookingStatus.CONFIRMED)
                .bookedAt(Instant.now())
                .build();

        return flightBookingRepository.save(flightBooking);
    }


    public List<FlightBooking> getUserBookings(String userId){
        return flightBookingRepository.findByUserIdOrderByDepartureTimeDesc(userId);
    }

    public FlightBooking updateBookingStatus(Long bookingId, String userId, BookingStatus newStatus){
        FlightBooking booking = flightBookingRepository.findById(bookingId).orElseThrow(
                ()-> new IllegalArgumentException("Booking not found")
        );

        if(!booking.getUserId().equals(userId)){
            throw new IllegalArgumentException("You can't modify other's booking");
        }

        booking.setBookingStatus(newStatus);
        return flightBookingRepository.save(booking);
    }


}
