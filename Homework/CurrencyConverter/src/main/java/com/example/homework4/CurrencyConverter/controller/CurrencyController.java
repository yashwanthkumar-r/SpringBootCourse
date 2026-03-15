package com.example.homework4.CurrencyConverter.controller;

import com.example.homework4.CurrencyConverter.advice.ApiResponse;
import com.example.homework4.CurrencyConverter.client.CurrencyClient;
import com.example.homework4.CurrencyConverter.dto.CurrConvertDto;
import com.example.homework4.CurrencyConverter.dto.CurrencyDto;
import com.example.homework4.CurrencyConverter.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyClient currencyClient;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/rate")
    public ApiResponse<CurrencyDto> getCurrentRate(@RequestParam String curName) {
        return currencyClient.getMoneyExchanged(curName);
    }

    @GetMapping("/convertCurrency")
    public ApiResponse<CurrConvertDto> getCurrencyConverter(@RequestParam String fromCurrency,
                                                            @RequestParam String toCurrency,
                                                            @RequestParam Double units) {
        ApiResponse<CurrencyDto> rate = currencyClient.getMoneyExchanged(toCurrency);
        return new ApiResponse<>(currencyService.getCurrencyConverter(rate.getData().getInr(), units));
    }
}
