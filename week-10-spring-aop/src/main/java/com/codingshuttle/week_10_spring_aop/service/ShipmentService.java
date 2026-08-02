package com.codingshuttle.week_10_spring_aop.service;

public interface ShipmentService {

    String orderPackage(Long orderId);

    String trackPackage(Long orderId);
}
