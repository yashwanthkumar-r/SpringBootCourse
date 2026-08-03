package com.codingshuttle.week_10_spring_aop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestShipmentServiceImpl {

    @Autowired
    private ShipmentService shipmentService;

    @Test
    public void testOrderPackage(){
        String orderString = shipmentService.orderPackage(474949L);
        log.info(orderString);
    }

    @Test
    public void testTrackPackage(){
        shipmentService.trackPackage(659493L);
    }
}
