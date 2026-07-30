package com.codingshuttle.week_09_learn_spring_ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookingsListResponse {

    List<BookingResponse> bookingResponseList;
    String message;
}
