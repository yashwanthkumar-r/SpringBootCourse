package com.example.homework4.CurrencyConverter.client;

import com.example.homework4.CurrencyConverter.advice.ApiResponse;
import com.example.homework4.CurrencyConverter.dto.CurrencyDto;

public interface CurrencyClient {

    ApiResponse<CurrencyDto> getMoneyExchanged(String reqCur);
}
