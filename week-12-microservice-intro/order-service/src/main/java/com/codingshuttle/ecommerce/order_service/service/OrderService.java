package com.codingshuttle.ecommerce.order_service.service;

import com.codingshuttle.ecommerce.order_service.dto.OrderRequestDto;
import com.codingshuttle.ecommerce.order_service.entity.Orders;
import com.codingshuttle.ecommerce.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrdersRepository ordersRepository;

    private final ModelMapper modelMapper;


    public List<OrderRequestDto> getAllOrders(){
        log.info("Fetching all Orders");
        List<Orders> orders = ordersRepository.findAll();

        return orders.stream().
                map(order -> modelMapper.map(order, OrderRequestDto.class))
                .toList();
    }

    public OrderRequestDto getOrderById(Long id){
        log.info("Fetching product with id: {}", id);
        Orders order =  ordersRepository.findById(id)
                .orElseThrow(()->new RuntimeException("orders not found in DB"));

        return modelMapper.map(order, OrderRequestDto.class);
    }
}
