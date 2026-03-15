package com.example.homework4.CurrencyConverter.services;

import com.example.homework4.CurrencyConverter.dto.CurrConvertDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    Logger log = LoggerFactory.getLogger(CurrencyService.class);

    public CurrConvertDto getCurrencyConverter(Double rate, Double units) {
        try {
            log.info("Entered into getCurrencyConverter method");
            Double rst = rate * units;
            log.info("Successfully converted USD to INR: {}", rst);
            return new CurrConvertDto(units, rst);
        } catch (Exception e) {
            log.error("Exception occurred at getCurrencyConverter: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
