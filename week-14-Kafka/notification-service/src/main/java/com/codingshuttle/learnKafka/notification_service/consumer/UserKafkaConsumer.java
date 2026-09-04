package com.codingshuttle.learnKafka.notification_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserKafkaConsumer {

    @KafkaListener(topics = {"user-topic"})
    public void handleUserRandomTopic1(String message){
        log.info("message received: {}", message);
    }

    @KafkaListener(topics = {"user-topic"})
    public void handleUserRandomTopic2(String message){
        log.info("message received: {}", message);
    }

    @KafkaListener(topics = {"user-topic"})
    public void handleUserRandomTopic3(String message){
        log.info("message received: {}", message);
    }
}
