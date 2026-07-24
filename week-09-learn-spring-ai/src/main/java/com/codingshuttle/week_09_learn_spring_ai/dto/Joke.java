package com.codingshuttle.week_09_learn_spring_ai.dto;

public record Joke(
        String text,
        String category,
        Double laughScore,
        Boolean isNSFW
) {
}
