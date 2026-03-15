package com.example.homework4.CurrencyConverter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrencyDto {

    @JsonProperty("INR")
    private Double inr;

}
