package com.codingshuttle.week_09_learn_spring_ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TravellingTools {

    @Tool(description = "Get the weather of a city")
    public String getWeather(@ToolParam(description = "City name for which to get weather information") String city){

        //here we can do API call or DB call. we have used switch for example
        return switch (city) {
            case "Edison" -> "Rainy, 60 Degrees";
            case "Delhi" -> "Sunny, 80 Degrees";
            case "NYC" -> "Cold, 40 Degrees";
            default -> "Can't identify the city";
        };
    }
}
