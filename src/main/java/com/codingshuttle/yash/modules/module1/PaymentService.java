package com.codingshuttle.yash.modules.module1;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component //This class object is managed by spring
public class PaymentService {
    public void pay(){
        System.out.println("paying...");
    }

    @PostConstruct
    public void beforePaying(){
        System.out.println("Before paying..");
    }

    @PreDestroy
    public void afterPaying(){
        System.out.println("After paying...");
    }
}
