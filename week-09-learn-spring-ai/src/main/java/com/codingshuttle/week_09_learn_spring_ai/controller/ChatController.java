package com.codingshuttle.week_09_learn_spring_ai.controller;

import com.codingshuttle.week_09_learn_spring_ai.tool.FlightBookingTools;
import com.codingshuttle.week_09_learn_spring_ai.tool.TravellingTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;
    private final TravellingTools travellingTools;
    private final FlightBookingTools flightBookingTools;
    private final ChatMemory chatMemory;

    @PostMapping("/chat")
    public String chat(@RequestBody String message, @RequestParam String userId){

        //userId should always be passed by security context, here it only used as example
        String systemPrompt = String.format("""
                You are a friendly flight booking assistant.
                Use the available tools to create, view, or update bookings.
                Always confirm actions with the user when possible.

                IMPORTANT: The current user's ID is "%s".
                When calling tools that require a userId, ALWAYS use this exact
                """, userId);

        return chatClient.prompt()
                .system(systemPrompt)
                .user(message)
                .tools(travellingTools, flightBookingTools)
                .advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build()
                )
                .advisors( advisorSpec ->
                    advisorSpec.param(CONVERSATION_ID,userId)
                )
                .call()
                .content();
    }

}
