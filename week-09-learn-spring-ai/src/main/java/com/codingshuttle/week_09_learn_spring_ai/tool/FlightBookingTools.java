package com.codingshuttle.week_09_learn_spring_ai.tool;

import com.codingshuttle.week_09_learn_spring_ai.dto.BookingResponse;
import com.codingshuttle.week_09_learn_spring_ai.dto.BookingsListResponse;
import com.codingshuttle.week_09_learn_spring_ai.entity.BookingStatus;
import com.codingshuttle.week_09_learn_spring_ai.entity.FlightBooking;
import com.codingshuttle.week_09_learn_spring_ai.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightBookingTools {

    private final FlightBookingService flightBookingService;

    @Tool(
            name = "flight_booking_tool",
            description = "Create a new flight booking for the user")
    public BookingResponse createBooking(
            @ToolParam(description = "The unique user id (e.g. userId is user123")
            String userId,
            @ToolParam(description = "The destination for the flight booking(e.g city like bengaluru, New York city")
            String destination,
            @ToolParam(description = "Department date and time in ISO-8601 format (e.g., 2026-07-29T14:30:00Z")
            Instant departureTime){

        FlightBooking booking = flightBookingService.createBooking(userId, destination, departureTime);

        return new BookingResponse(
                booking.getId(),
                booking.getUserId(),
                booking.getDestination(),
                booking.getDepartureTime(),
                booking.getBookingStatus(),
                booking.getBookedAt()
        );

    }

    @Tool(
            name = "get_user_bookings",
            description = "Retrieve all the  flight bookings for the current user, sorted by departure time (mosrt recent first)."
            + "Return an empty list message if none exist."
    )
    public BookingsListResponse getUserBookings(
            @ToolParam(description = "The unique user ID")
            String userId
    ){
        List<FlightBooking> bookings = flightBookingService.getUserBookings(userId);

        List<BookingResponse> bookingResponses = bookings.stream()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getUserId(),
                        booking.getDestination(),
                        booking.getDepartureTime(),
                        booking.getBookingStatus(),
                        booking.getBookedAt()
                )).toList();

        String message = bookings.isEmpty()
                ? "You have no upcoming flight bookings."
                : "Here are your current flight bookings:";

        return new BookingsListResponse(bookingResponses, message);
     }

    @Tool(
            name = "update_booking_status",
            description = "Update the status of an existing flight booking (e.g., cancel it)." +
                     "Only the owner of the booking can modify it. " +
                    "Common use: set status to CANCELLED."
    )public BookingResponse updateBookingStatus(
            @ToolParam(description = "The booking Id returned from create or get bookings", required = true)
            Long bookingId,
            @ToolParam(description = "The unique user id who owns the booking", required = true)
            String userId,
            @ToolParam(description = "New Status: CONFIRMED, CANCELLED or PENDING", required = true)
            BookingStatus newStatus){

        FlightBooking updated = flightBookingService.updateBookingStatus(bookingId, userId, newStatus);

        return new BookingResponse(
                updated.getId(),
                updated.getUserId(),
                updated.getDestination(),
                updated.getDepartureTime(),
                updated.getBookingStatus(),
                updated.getBookedAt()
        );
    }


}
