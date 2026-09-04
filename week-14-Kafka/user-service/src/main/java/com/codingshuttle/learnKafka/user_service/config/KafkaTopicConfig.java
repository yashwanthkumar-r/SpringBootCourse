package com.codingshuttle.learnKafka.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.user-topic}")
    private String KAFKA_USER_TOPIC;

    @Bean
    public NewTopic userTopic(){
        return new NewTopic(KAFKA_USER_TOPIC, 3, (short) 1);
    }
}
