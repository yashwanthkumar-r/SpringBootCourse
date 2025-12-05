package com.codingshuttle.yash.modules.module1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    PaymentService paymentService(){
        //more Logic
        return new PaymentService();
    }
}
