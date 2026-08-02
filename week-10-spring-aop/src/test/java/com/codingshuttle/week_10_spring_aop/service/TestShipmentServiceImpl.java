package com.codingshuttle.week_10_spring_aop.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestShipmentServiceImpl {

    @Autowired
    private ShipmentService shipmentService;

    @Test
    public void testOrderPackage(){
        shipmentService.orderPackage(474949L);
    }

    @Test
    public void testTrackPackage(){
        shipmentService.trackPackage(659493L);
    }
}
